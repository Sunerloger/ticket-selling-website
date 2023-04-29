package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import jakarta.validation.constraints.NotNull;

public class HallPlanDto {

    @NotNull(message = "name must be specified")
    private String name;
    private String description;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public static final class HallPlanDtoBuilder {

        private String name;
        private String description;
        private HallPlanDtoBuilder() {

        }
        public static HallPlanDtoBuilder aHallPlanDto() { return new HallPlanDtoBuilder(); }

        public HallPlanDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }
        public HallPlanDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public HallPlanDto build() {
            HallPlanDto hallPlanDto = new HallPlanDto();
            hallPlanDto.setName(name);
            hallPlanDto.setDescription(description);
            return hallPlanDto;
        }

    }
}
