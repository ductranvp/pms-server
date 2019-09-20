package vn.ptit.pms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Object getFieldValue() {
        return this.fieldValue;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ResourceNotFoundException)) return false;
        final ResourceNotFoundException other = (ResourceNotFoundException) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$resourceName = this.getResourceName();
        final Object other$resourceName = other.getResourceName();
        if (this$resourceName == null ? other$resourceName != null : !this$resourceName.equals(other$resourceName))
            return false;
        final Object this$fieldName = this.getFieldName();
        final Object other$fieldName = other.getFieldName();
        if (this$fieldName == null ? other$fieldName != null : !this$fieldName.equals(other$fieldName)) return false;
        final Object this$fieldValue = this.getFieldValue();
        final Object other$fieldValue = other.getFieldValue();
        if (this$fieldValue == null ? other$fieldValue != null : !this$fieldValue.equals(other$fieldValue))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResourceNotFoundException;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $resourceName = this.getResourceName();
        result = result * PRIME + ($resourceName == null ? 43 : $resourceName.hashCode());
        final Object $fieldName = this.getFieldName();
        result = result * PRIME + ($fieldName == null ? 43 : $fieldName.hashCode());
        final Object $fieldValue = this.getFieldValue();
        result = result * PRIME + ($fieldValue == null ? 43 : $fieldValue.hashCode());
        return result;
    }

    public String toString() {
        return "ResourceNotFoundException(resourceName=" + this.getResourceName() + ", fieldName=" + this.getFieldName() + ", fieldValue=" + this.getFieldValue() + ")";
    }
}
