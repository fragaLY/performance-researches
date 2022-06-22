package by.vk.datagen.data.userstransfers;

import by.vk.datagen.data.transfer.Transfer;
import by.vk.datagen.data.user.User;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Table(schema = "a2b", name = "users_transfers")
@Entity
@Getter
@Setter
@ToString
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    UsersTransfers that = (UsersTransfers) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
