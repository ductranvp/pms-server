package vn.ptit.pms.service.dto.core;

public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;

    public ChangePasswordDto() {
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ChangePasswordDto)) return false;
        final ChangePasswordDto other = (ChangePasswordDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$oldPassword = this.getOldPassword();
        final Object other$oldPassword = other.getOldPassword();
        if (this$oldPassword == null ? other$oldPassword != null : !this$oldPassword.equals(other$oldPassword))
            return false;
        final Object this$newPassword = this.getNewPassword();
        final Object other$newPassword = other.getNewPassword();
        if (this$newPassword == null ? other$newPassword != null : !this$newPassword.equals(other$newPassword))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ChangePasswordDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $oldPassword = this.getOldPassword();
        result = result * PRIME + ($oldPassword == null ? 43 : $oldPassword.hashCode());
        final Object $newPassword = this.getNewPassword();
        result = result * PRIME + ($newPassword == null ? 43 : $newPassword.hashCode());
        return result;
    }

    public String toString() {
        return "ChangePasswordDto(oldPassword=" + this.getOldPassword() + ", newPassword=" + this.getNewPassword() + ")";
    }
}
