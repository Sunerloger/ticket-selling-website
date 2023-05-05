package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;


import java.util.Arrays;

public class ImageDataDto {


    private byte[] imageData;


    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageDataDto that)) {
            return false;
        }
        return Arrays.equals(imageData, that.imageData);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(imageData);
    }


    public static final class ImageDataDtoBuilder {

        private byte[] imageData;

        private ImageDataDtoBuilder() {
        }

        public static ImageDataDto.ImageDataDtoBuilder aImageDataDto() {
            return new ImageDataDto.ImageDataDtoBuilder();
        }

        public ImageDataDto.ImageDataDtoBuilder withImageData(byte[] imageData) {
            this.imageData = imageData;
            return this;
        }

        public ImageDataDto build() {
            ImageDataDto imageDataDto = new ImageDataDto();
            imageDataDto.setImageData(imageData);
            return imageDataDto;
        }
    }
}
