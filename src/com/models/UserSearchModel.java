package com.models;

public class UserSearchModel {

	private int id;
	private String skill;
	private String firstName;
	private String lastName;
	private String email;
	private String userType;
	private String latitude;
	private String longitude;

	private String accuracy;
	private String contactMedium;
	private String userImage;

	private String location;

	private String country;
	private String state;
	private String city;
	private String certification;
	private String organisation;
	private String address;
	private String postalCode;
	private String mobile;
	private String webSite;
	private String education;
	private String otherQualification;
	private String personalDetail;
	private String userName;
	private String introduction;
	private String rating;
	private String dateOfBirth;
	
	private String gender;

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getWorkingHour() {
		return workingHour;
	}

	public void setWorkingHour(String workingHour) {
		if (workingHour == null || workingHour.equalsIgnoreCase("null")) {
			workingHour = "";
		}
		this.workingHour = workingHour;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		if (experience == null || experience.equalsIgnoreCase("null")) {
			experience = "";
		}
		this.experience = experience;
	}

	private String workingHour;
	private String experience;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		if (country == null || country.equalsIgnoreCase("null")) {
			country = "";
		}
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state == null || state.equalsIgnoreCase("null")) {
			state = "";
		}
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if (city == null || city.equalsIgnoreCase("null")) {
			city = "";
		}
		this.city = city;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		if (certification == null || certification.equalsIgnoreCase("null")) {
			certification = "";
		}
		this.certification = certification;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		if (organisation == null || organisation.equalsIgnoreCase("null")) {
			organisation = "";
		}
		this.organisation = organisation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (address == null || address.equalsIgnoreCase("null")) {
			address = "";
		}
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		if (postalCode == null || postalCode.equalsIgnoreCase("null")) {
			postalCode = "";
		}
		this.postalCode = postalCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		if (mobile == null || mobile.equalsIgnoreCase("null")) {
			mobile = "";
		}
		this.mobile = mobile;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		if (webSite == null || webSite.equalsIgnoreCase("null")) {
			webSite = "";
		}
		this.webSite = webSite;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		if (education == null || education.equalsIgnoreCase("null")) {
			education = "";
		}
		this.education = education;
	}

	public String getOtherQualification() {
		return otherQualification;
	}

	public void setOtherQualification(String otherQualification) {
		if (otherQualification == null
				|| otherQualification.equalsIgnoreCase("null")) {
			otherQualification = "";
		}
		this.otherQualification = otherQualification;
	}

	public String getPersonalDetail() {
		return personalDetail;
	}

	public void setPersonalDetail(String personalDetail) {
		if (personalDetail == null || personalDetail.equalsIgnoreCase("null")) {
			personalDetail = "";
		}
		this.personalDetail = personalDetail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		if (skill == null || skill.equalsIgnoreCase("null")) {
			skill = "";
		}
		this.skill = skill;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null || firstName.equalsIgnoreCase("null")) {
			firstName = "";
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName == null || lastName.equalsIgnoreCase("null")) {
			lastName = "";
		}
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null || email.equalsIgnoreCase("null")) {
			email = "";
		}
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		if (userType == null || userType.equalsIgnoreCase("null")) {
			userType = "";
		}
		this.userType = userType;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		if (latitude == null || latitude.equalsIgnoreCase("null")) {
			latitude = "";
		}
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		if (longitude == null || longitude.equalsIgnoreCase("null")) {
			longitude = "";
		}
		this.longitude = longitude;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		if (accuracy == null || accuracy.equalsIgnoreCase("null")) {
			accuracy = "";
		}
		this.accuracy = accuracy;
	}

	public String getContactMedium() {
		return contactMedium;
	}

	public void setContactMedium(String contactMedium) {
		if (contactMedium == null || contactMedium.equalsIgnoreCase("null")) {
			contactMedium = "";
		}
		this.contactMedium = contactMedium;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		if (userImage == null || userImage.equalsIgnoreCase("null")) {
			userImage = "";
		}
		this.userImage = userImage;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		if (location == null || location.equalsIgnoreCase("null")) {
			location = "";
		}
		this.location = location;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		if (userName == null || userName.equalsIgnoreCase("null")) {
			userName = "";
		}
		this.userName = userName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		if (introduction == null || introduction.equalsIgnoreCase("null")) {
			introduction = "";
		}
		this.introduction = introduction;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		if (dateOfBirth == null || dateOfBirth.equalsIgnoreCase("null")) {
			dateOfBirth = "";
		}
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
