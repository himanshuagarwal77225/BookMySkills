package com.utils.network;

public class WebUtils {

	public static final String LIVE_API = "http://www.bookmyskills.com/apps/api.php";

	public static final String BASE_URL = LIVE_API;
	

	/**
	 * @GETSKILL {@link requires}
	 */
	public static final String GETSKILL = BASE_URL + "?todo=getskill";

	/**
	 * @LOGIN {@link requires} <br>
	 *        user<br>
	 *        password
	 * 
	 */
	public static final String LOGIN = BASE_URL + "?todo=login&";

	/**
	 * @GETSEARCH {@link requires} <br>
	 *            search<br>
	 * 
	 */
	public static final String GETSEARCH = BASE_URL + "?todo=getsearch&";

	/**
	 * @GETPROFILE {@link requires} <br>
	 *             id<br>
	 *             token<br>
	 *             user_id
	 * 
	 */
	public static final String GETPROFILE = BASE_URL + "?todo=getprofile&";

	/**
	 * @EDIT_PROFILE {@link requires} <br>
	 *               first_name<br>
	 *               last_name<br>
	 *               country_id<br>
	 *               state_id<br>
	 *               city_id<br>
	 *               address<br>
	 *               postal_code<br>
	 *               mobile<br>
	 *               device_email_id<br>
	 *               imei<br>
	 *               organistaion<br>
	 *               website<br>
	 *               preferred_contact_medium<br>
	 *               login_id<br>
	 *               user_id
	 * 
	 */
	public static final String EDIT_PROFILE = BASE_URL + "?todo=edit_profile&";

	/**
	 * @GETPRIVACYPOLICY {@link requires} <br>
	 * 
	 */
	public static final String GETPRIVACYPOLICY = BASE_URL
			+ "?todo=getprivacy_policy";

	/**
	 * @GETTERMSCONDITIONS {@link requires} <br>
	 * 
	 */
	public static final String GETTERMSCONDITIONS = BASE_URL + "?todo=gett_c";

	/**
	 * @INBOX {@link requires} <br>
	 *        user_id<br>
	 *        token
	 * 
	 */
	public static final String INBOX = BASE_URL + "?todo=inbox&";

	/**
	 * @SEND_MESSAGE {@link requires} <br>
	 *               user_id<br>
	 *               token<br>
	 *               to_id<br>
	 *               from_id<br>
	 *               subject<br>
	 *               body
	 * 
	 */
	public static final String SEND_MESSAGE = BASE_URL + "?todo=send_message&";

	/**
	 * @GETCOUNTRY {@link requires} <br>
	 * 
	 */
	public static final String GETCOUNTRY = BASE_URL + "?todo=getcountry";

	/**
	 * @GETSTATE {@link requires} <br>
	 * 
	 */
	public static final String GETSTATE = BASE_URL + "?todo=getstate&";

	/**
	 * @GETCITY {@link requires} <br>
	 * 
	 */
	public static final String GETCITY = BASE_URL + "?todo=getcity&";

	/**
	 * @GETREGISTER {@link requires} <br>
	 *              user_type<br>
	 *              username<br>
	 *              email<br>
	 * 
	 */
	public static final String GETREGISTER = BASE_URL + "?todo=register&";

	/**
	 * @RESET_PASSWORD {@link requires} <br>
	 *                 username<br>
	 * 
	 */
	public static final String RESET_PASSWORD = BASE_URL
			+ "?todo=reset_password&";

	/**
	 * @LOGOUT {@link requires} <br>
	 *         user_id<br>
	 *         token
	 * 
	 */
	public static final String LOGOUT = BASE_URL + "?todo=logout&";

	/**
	 * @SETGCM {@link requires} <br>
	 *         user_id<br>
	 *         token<br>
	 *         gcm_regid<br>
	 *         email
	 * 
	 */
	public static final String SETGCM = BASE_URL + "?todo=setgcm&";

	/**
	 * @SETGCM {@link requires} <br>
	 *         user_id<br>
	 *         latitude<br>
	 *         longitude<br>
	 *         accuracy
	 * 
	 */
	public static final String TRACK_USER = BASE_URL + "?todo=track_user&";
	
	public static final String IMAGE_URL = "http://www.bookmyskills.com/apps/images/";

}
