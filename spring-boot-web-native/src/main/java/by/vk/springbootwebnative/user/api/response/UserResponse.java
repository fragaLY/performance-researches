package by.vk.springbootwebnative.user.api.response;

import by.vk.springbootwebnative.user.repository.user.User;

public record UserResponse(Long id, String name, String email, String phone) {

  public static UserResponse from(User entity) {
    return new UserResponse(entity.getId(), entity.getFirstName() + " " + entity.getLastName(),
        entity.getEmail(), entity.getPhone());
  }
}
