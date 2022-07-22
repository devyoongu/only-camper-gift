package dev.practice.gift.interfaces.api;

import dev.practice.gift.application.GiftFacade;
import dev.practice.gift.common.response.CommonResponse;
import dev.practice.gift.domain.gift.Gift;
import dev.practice.gift.domain.gift.GiftInfo;
import dev.practice.gift.domain.gift.GiftService;
import dev.practice.gift.infrastructure.gift.GiftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gifts")
public class GiftApiController {
    private final GiftFacade giftFacade;
    private final GiftDtoMapper giftDtoMapper;

    private final GiftRepository giftRepository;

    private final GiftService giftService;

    @GetMapping("/{giftToken}")
    public CommonResponse retrieveOrder(@PathVariable String giftToken) {
        var giftInfo = giftFacade.getOrder(giftToken);
        return CommonResponse.success(giftInfo);
    }

    @GetMapping
    public CommonResponse giftList(Long giftReceiverUserId) {
        List<GiftInfo> giftInfoList = giftService.getGiftInfoList(giftReceiverUserId);

        return CommonResponse.success(giftInfoList);
    }

    @PostMapping
    public CommonResponse registerOrder(@RequestBody @Valid GiftDto.RegisterReq request) {
        var giftCommand = giftDtoMapper.of(request);
        var giftInfo = giftFacade.registerOrder(giftCommand);
        return CommonResponse.success(new GiftDto.RegisterRes(giftInfo));
    }

    @PostMapping("/{giftToken}/payment-processing")
    public CommonResponse requestPaymentProcessing(@PathVariable String giftToken) {
        giftFacade.requestPaymentProcessing(giftToken);
        return CommonResponse.success("OK");
    }

    @PostMapping("/{giftToken}/accept-gift")
    public CommonResponse acceptGift(
            @PathVariable String giftToken,
            @RequestBody @Valid GiftDto.AcceptGiftReq request
    ) {
        var acceptCommand = giftDtoMapper.of(giftToken, request);
        giftFacade.acceptGift(acceptCommand);
        return CommonResponse.success("OK");
    }
}
