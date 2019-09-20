package vn.ptit.pms.service.dto.core;

public class ResetPasswordDto {
    private String key;
    private String newPassword;

    public ResetPasswordDto() {
    }

    public String getKey() {
        return this.key;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ResetPasswordDto)) return false;
        final ResetPasswordDto other = (ResetPasswordDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$key = this.getKey();
        final Object other$key = other.getKey();
        if (this$key == null ? other$key != null : !this$key.equals(other$key)) return false;
        final Object this$newPassword = this.getNewPassword();
        final Object other$newPassword = other.getNewPassword();
        if (this$newPassword == null ? other$newPassword != null : !this$newPassword.equals(other$newPassword))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResetPasswordDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $key = this.getKey();
        result = result * PRIME + ($key == null ? 43 : $key.hashCode());
        final Object $newPassword = this.getNewPassword();
        result = result * PRIME + ($newPassword == null ? 43 : $newPassword.hashCode());
        return result;
    }

    public String toString() {
        return "ResetPasswordDto(key=" + this.getKey() + ", newPassword=" + this.getNewPassword() + ")";
    }
}
