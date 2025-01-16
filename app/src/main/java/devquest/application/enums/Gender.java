package devquest.application.enums;

import lombok.Getter;

@Getter
public enum Gender {

  MASCULINO("MASCULINO"),
  FEMININO("FEMININO");

  private final String gender;

  Gender(String gender) {
    this.gender = gender;
  }

  public Gender getGenderByString(String genderName) {
    for (Gender g : Gender.values()) {
      if (g.getGender().equalsIgnoreCase(genderName)) return g;
    }

    throw new IllegalArgumentException("Gender not found");
  }

}
