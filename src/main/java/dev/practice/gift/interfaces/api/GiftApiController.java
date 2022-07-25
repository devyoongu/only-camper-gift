package dev.practice.gift.interfaces.api;

import dev.practice.gift.application.GiftFacade;
import dev.practice.gift.common.response.CommonResponse;
import dev.practice.gift.domain.gift.GiftInfo;
import dev.practice.gift.domain.gift.GiftService;
import dev.practice.gift.infrastructure.gift.GiftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gifts")
public class GiftApiController {
    private final GiftFacade giftFacade;
    private final GiftDtoMapper giftDtoMapper;
    private final GiftService giftService;

    @GetMapping("/{giftToken}")
    public CommonResponse retrieveOrder(@PathVariable String giftToken) {
        var giftInfo = giftFacade.getOrder(giftToken);
        return CommonResponse.success(giftInfo);
    }

    @GetMapping
    public CommonResponse findByGiftReceiverUserId(@RequestParam("giftReceiverUserId") Long giftReceiverUserId, @RequestParam(required=false) String status) {
        List<GiftInfo> giftInfoList = giftService.findByGiftReceiverUserId(giftReceiverUserId,status);

        return CommonResponse.success(giftInfoList);
    }

    /**선물하기 등록*/
    @PostMapping
    public CommonResponse registerOrder(@RequestBody @Valid GiftDto.RegisterReq request) {
        var giftCommand = giftDtoMapper.of(request);
        var giftInfo = giftFacade.registerOrder(giftCommand);
        return CommonResponse.success(new GiftDto.RegisterRes(giftInfo));
    }

    /**
     * 선물 결제
     * - order에서 호출
     * */
    @PostMapping("/{giftToken}/payment-processing")
    public CommonResponse requestPaymentProcessing(@PathVariable String giftToken) {
        giftFacade.requestPaymentProcessing(giftToken);
        return CommonResponse.success("OK");
    }

    /**
     * 선물수락
     * */
    @PostMapping("/{giftToken}/accept-gift")
    public CommonResponse acceptGift(
            @PathVariable String giftToken,
            @RequestBody @Valid GiftDto.AcceptGiftReq request,
            BindingResult bindingResult
    ) {
        var acceptCommand = giftDtoMapper.of(giftToken, request);
        giftFacade.acceptGift(acceptCommand);
        return CommonResponse.success("OK");
    }
}
