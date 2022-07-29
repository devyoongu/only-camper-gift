package dev.practice.gift.domain.gift;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@ToString
public class GiftInfo {
    private final String orderToken;
    private final String giftToken;
    private final Gift.PushType pushType;
    private Long buyerUserId;
    private final String giftReceiverName;
    private final String giftReceiverPhone;
    private final String giftMessage;
    private final String statusDesc;
    private final String statusName;
    private final LocalDateTime paidAt;

    public GiftInfo(Gift gift) {
        this.orderToken = gift.getOrderToken();
        this.giftToken = gift.getGiftToken();
        this.pushType = gift.getPushType();
        this.buyerUserId = gift.getBuyerUserId();
        this.giftReceiverName = gift.getGiftReceiverName();
        this.giftReceiverPhone = gift.getReceiverPhone();
        this.giftMessage = gift.getGiftMessage();
        this.statusDesc = gift.getStatus().getDescription();
        this.statusName = gift.getStatus().name();
        this.paidAt = gift.getPaidAt();
    }
}
