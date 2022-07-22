package dev.practice.gift.domain.gift;

import java.util.List;

public interface GiftService {

    GiftInfo getGiftInfo(String giftToken);
    List<GiftInfo> getGiftInfoList(Long giftReceiverUserId);

    GiftInfo registerOrder(GiftCommand.Register giftCommand);

    void requestPaymentProcessing(String giftToken);

    void completePayment(String orderToken);

    void acceptGift(GiftCommand.Accept request);
}
