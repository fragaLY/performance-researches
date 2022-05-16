package by.vk.datagen.data.userstransfers;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class UsersTransfersId implements Serializable {

    @Serial
    private static final long serialVersionUID = 419403237500196989L;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "transfer_id")
    Long transferId;
}
