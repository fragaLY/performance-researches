package by.vk.user.transfers;

import by.vk.transfer.Transfer;
import by.vk.user.User;
import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

enum State {
  BOOKED,
  COMPLETED,
  CANCELED
}

@Serdeable
@MappedEntity(value = "users_transfers", schema = "a2b")
public class UsersTransfers {

  @EmbeddedId
  private UsersTransfersId id;

  @Relation(Relation.Kind.MANY_TO_ONE)
  private User user;

  @Relation(Relation.Kind.MANY_TO_ONE)
  private Transfer transfer;

  @Enumerated(EnumType.STRING)
  private State state;

  private String description;

  public UsersTransfers() {
  }

  public UsersTransfers(UsersTransfersId id, User user, Transfer transfer, State state,
      String description) {
    this.id = id;
    this.user = user;
    this.transfer = transfer;
    this.state = state;
    this.description = description;
  }

  public UsersTransfersId getId() {
    return id;
  }

  public void setId(UsersTransfersId id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Transfer getTransfer() {
    return transfer;
  }

  public void setTransfer(Transfer transfer) {
    this.transfer = transfer;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UsersTransfers that)) {
      return false;
    }
    if (state != that.state) {
      return false;
    }
    return Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    int result = state != null ? state.hashCode() : 0;
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UsersTransfers{" +
        "id=" + id +
        ", state=" + state +
        ", description='" + description + '\'' +
        '}';
  }
}

@Serdeable
@Embeddable
class UsersTransfersId implements Serializable {

  @MappedProperty(value = "user_id")
  public Long userId;

  @MappedProperty(value = "transfer_id")
  public Long transferId;

  public UsersTransfersId() {
  }

  public UsersTransfersId(Long userId, Long transferId) {
    this.userId = userId;
    this.transferId = transferId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getTransferId() {
    return transferId;
  }

  public void setTransferId(Long transferId) {
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
    int result = userId.hashCode();
    result = 31 * result + transferId.hashCode();
    return result;
  }
}