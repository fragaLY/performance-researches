package by.vk.springbootweb.user.repository.usertransfer;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsersTransfersId implements Serializable {

    @Serial
    private static final long serialVersionUID = 419403237500196989L;

    @Column(name = "user_id")
    public Long userId;

    @Column(name = "transfer_id")
    public Long transferId;

    public UsersTransfersId() {
    }

    public UsersTransfersId(Long userId, Long transferId) {
        this.userId = userId;
        this.transferId = transferId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsersTransfersId that = (UsersTransfersId) o;
        return userId != null && Objects.equals(userId, that.userId)
                && transferId != null && Objects.equals(transferId, that.transferId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, transferId);
    }
}