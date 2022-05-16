package by.vk.datagen.data.userstransfers;

import by.vk.datagen.data.transfer.Transfer;
import by.vk.datagen.data.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Table(schema = "a2b", name = "users_transfers")
@Data
@RequiredArgsConstructor
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersTransfers {

    @EmbeddedId
    private UsersTransfersId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("transferId")
    @JoinColumn(name = "transfer_id")
    private Transfer transfer;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(columnDefinition = "text")
    private String description;
}
