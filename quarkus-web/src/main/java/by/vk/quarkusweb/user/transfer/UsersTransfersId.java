package by.vk.quarkusweb.user.transfer;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UsersTransfersId implements Serializable {

  @Serial
  private static final long serialVersionUID = 419403237500196989L;
  public Long userId;
  public Long transferId;

  public UsersTransfersId() {
  }

  public UsersTransfersId(Long userId, Long transferId) {
    this.userId = userId;
    this.transferId = transferId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UsersTransfersId that)) {
      return false;
    }

    if (!userId.equals(that.userId)) {
      return false;
    }
    return transferId.equals(that.transferId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, transferId);
  }
}
