package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class HallPlanSectionDto {
    private Long id;
    private String name;
    private String color;
    private Long price;
    private Long hallplan_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getHallplan_id() {
        return hallplan_id;
    }

    public void setHallplan_id(Long hallplan_id) {
        this.hallplan_id = hallplan_id;
    }

    public static final class HallPlanSectionDtoBuilder {

        private Long id;
        private String name;
        private String color;
        private Long price;
        private Long hallplan_id;

        private HallPlanSectionDtoBuilder() {

        }

        public static HallPlanSectionDtoBuilder aHallPlanSectionDto() {
            return new HallPlanSectionDtoBuilder();
        }

        public HallPlanSectionDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public HallPlanSectionDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public HallPlanSectionDtoBuilder withColor(String color) {
            this.color = color;
            return this;
        }

        public HallPlanSectionDtoBuilder withPrice(Long price) {
            this.price = price;
            return this;
        }

        public HallPlanSectionDtoBuilder withHallPlanId(Long hallplan_id) {
            this.hallplan_id = hallplan_id;
            return this;
        }

        public HallPlanSectionDto build() {
            HallPlanSectionDto hallPlanSectionDto = new HallPlanSectionDto();
            hallPlanSectionDto.setId(id);
            hallPlanSectionDto.setName(name);
            hallPlanSectionDto.setColor(color);
            hallPlanSectionDto.setPrice(price);
            hallPlanSectionDto.setHallplan_id(hallplan_id);
            return hallPlanSectionDto;
        }

    }

}
