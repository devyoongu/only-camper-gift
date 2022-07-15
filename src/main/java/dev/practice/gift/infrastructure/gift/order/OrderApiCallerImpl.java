package dev.practice.gift.infrastructure.gift.order;

import dev.practice.gift.common.response.CommonResponse;
import dev.practice.gift.domain.gift.GiftCommand;
import dev.practice.gift.domain.gift.order.OrderApiCaller;
import dev.practice.gift.domain.gift.order.OrderApiCommand;
import dev.practice.gift.infrastructure.retrofit.RetrofitUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderApiCallerImpl implements OrderApiCaller {
    private final RetrofitUtils retrofitUtils;
    private final RetrofitOrderApi retrofitOrderApi;

    @Override
    public String registerGiftOrder(OrderApiCommand.Register request) {
        var call = retrofitOrderApi.registerOrder(request);//call을 execute 해야만 그때 api call이 된다.
        return retrofitUtils.responseSync(call)
                //repsponse를 CommonResponse 타입으로 변환하여 getData 한 후
                .map(CommonResponse::getData)
                //RetrofitOrderApiResponse.Register 변환하여 getOrderToken 한다.
                .map(RetrofitOrderApiResponse.Register::getOrderToken)
                //retrofit의 responseSync 호출이 Optional로 감싸져 있기 때문에
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateReceiverInfo(String orderToken, GiftCommand.Accept request) {
        var call = retrofitOrderApi.updateReceiverInfo(orderToken, request);
        retrofitUtils.responseVoid(call);
    }
}
