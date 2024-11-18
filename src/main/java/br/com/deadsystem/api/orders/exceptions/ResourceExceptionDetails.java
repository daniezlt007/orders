package br.com.deadsystem.api.orders.exceptions;

import java.time.LocalDateTime;

public class ResourceExceptionDetails {

    private String title;
    private int status;
    private String detail;
    private LocalDateTime timestamp;
    private String developerMessage;

    private ResourceExceptionDetails() {
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public static final class Builder {
        private String title;
        private int status;
        private String detail;
        private LocalDateTime timestamp;
        private String developerMessage;

        private Builder() {
        }

        public static Builder aResourceNotFoundDetails() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ResourceExceptionDetails build() {
            ResourceExceptionDetails resourceExceptionDetails = new ResourceExceptionDetails();
            resourceExceptionDetails.status = this.status;
            resourceExceptionDetails.detail = this.detail;
            resourceExceptionDetails.timestamp = this.timestamp;
            resourceExceptionDetails.title = this.title;
            resourceExceptionDetails.developerMessage = this.developerMessage;
            return resourceExceptionDetails;
        }
    }
}
