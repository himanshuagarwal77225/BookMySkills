package com.bookmyskills.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WebServiceFunctions {

	private JSONParser jsonParser;

	public WebServiceFunctions() {
		jsonParser = new JSONParser();
	}

	/**
	 * function make getServerConfig Request
	 * 
	 * @param appVersion
	 * @param iOSVersion
	 * @param devToken
	 * @param devModel
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject getServerConfig(String appVersion, String androidVersion,
			String devToken, String DevModel, String seq) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(GlobalConst.appVersionTag, appVersion));
		params.add(new BasicNameValuePair(GlobalConst.iOSVersionTag,
				androidVersion));
		params.add(new BasicNameValuePair(GlobalConst.devTokenTag, devToken));
		params.add(new BasicNameValuePair(GlobalConst.devModelTag, DevModel));
		params.add(new BasicNameValuePair(GlobalConst.seqTag, seq));
		JSONObject jsonResponseObject = jsonParser.makeServiceCall(
				GlobalConst.ServerBaseURL + GlobalConst.GETSERVERCONFIG,
				JSONParser.GET, params);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function get Server Config detail for first run
	 * 
	 * @return JSONObject
	 * */
	public JSONObject getServerConfig(String appVersion) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(GlobalConst.appVersionTag, appVersion));

		JSONObject jsonResponseObject = jsonParser.makeServiceCall(
				GlobalConst.ServerBaseURL + GlobalConst.GETSERVERCONFIG,
				JSONParser.GET, params);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @param password
	 * @param appversion
	 * @return JSONObject
	 * */
	public JSONObject loginUser(String email, String password, String appVersion) {
		// Building Parameters
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("email", email));
		// params.add(new BasicNameValuePair("password", password));

		JSONObject object = new JSONObject();
		try {
			JSONObject object2 = new JSONObject();
			object2.put(GlobalConst.rTag, GlobalConst.loginReqTag);
			object2.put(GlobalConst.emailTag, email);
			object2.put(GlobalConst.passwordTag, password);
			object2.put(GlobalConst.formatTag, "json");
			object2.put(GlobalConst.appVersionTag, appVersion);

			object.put(GlobalConst.loginReqTag, object2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponseObject = jsonParser.getJSONFromUrl(
				GlobalConst.ServerBaseURL + GlobalConst.loginURL, object);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make Register Request
	 * 
	 * @param email
	 * @param auth
	 * @param password
	 * @param nickName
	 * @param sex
	 * @param birthday
	 * @param gps
	 * @param mode
	 * @param some
	 * @param figure
	 * @param mSex
	 * @param mAgeMin
	 * @param mAgeMax
	 * @param mDist
	 * @param status
	 * @param seq
	 * @param appVersion
	 * @return JSONObject
	 * */
	public JSONObject registerUser(String email, String auth, String password,
			String nickName, String sex, String birthday, String gps,
			String mode, String some, String figure, String mSex,
			String mAgeMin, String mAgeMax, String mDist, String status,
			String seq, String appVersion, String deviceToken, boolean isSocial) {
		// Building Parameters
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("email", email));
		// params.add(new BasicNameValuePair("password", password));

		JSONObject object = new JSONObject();
		try {
			JSONObject object2 = new JSONObject();
			object2.put(GlobalConst.rTag, GlobalConst.registerReqTag);
			object2.put(GlobalConst.emailTag, email);
			object2.put(GlobalConst.passwordTag, password);
			object2.put(GlobalConst.nicknameTag, nickName);
			object2.put(GlobalConst.gpsTag, gps);
			object2.put(GlobalConst.figureTag, figure);
			if (isSocial) {
				object2.put(GlobalConst.authTag, auth);
				object2.put(GlobalConst.sexTag, sex);
				object2.put(GlobalConst.birthdayTag, birthday);
				object2.put(GlobalConst.modeTag, mode);
				object2.put(GlobalConst.someTag, some);
				object2.put(GlobalConst.mSexTag, mSex);
				object2.put(GlobalConst.mAgeMinTag, mAgeMin);
				object2.put(GlobalConst.mDistTag, mDist);
				object2.put(GlobalConst.statusTag, status);
				object2.put(GlobalConst.seqTag, seq);
			}
			object2.put(GlobalConst.formatTag, "json");
			object2.put(GlobalConst.appVersionTag, appVersion);
			object2.put(GlobalConst.devTokenTag, deviceToken);
			object.put(GlobalConst.registerReqTag, object2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponseObject = jsonParser.getJSONFromUrl(
				GlobalConst.ServerBaseURL + GlobalConst.registerURL, object);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make Logout user
	 * 
	 * @param token
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject logoutUser(String token, String seq) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(GlobalConst.tokenTag, token));
		params.add(new BasicNameValuePair(GlobalConst.seqTag, seq));
		JSONObject jsonResponseObject = jsonParser.makeServiceCall(
				GlobalConst.ServerBaseURL + GlobalConst.logoutURL,
				JSONParser.GET, params);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make UnRegister user
	 * 
	 * @param token
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject unRegister(String token, String seq) {

		JSONObject object = new JSONObject();
		try {
			JSONObject object2 = new JSONObject();
			object2.put(GlobalConst.rTag, GlobalConst.unregisterReqTag);
			object2.put(GlobalConst.tokenTag, token);
			object2.put(GlobalConst.seqTag, seq);
			object2.put(GlobalConst.formatTag, "json");
			object.put(GlobalConst.unregisterReqTag, object2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponseObject = jsonParser.getJSONFromUrl(
				GlobalConst.ServerBaseURL + GlobalConst.unregisterURL, object);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make getUserConfig
	 * 
	 * @param token
	 * @param uids
	 * @param appversion
	 * @param keywords
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject getUserConfig(String token, String uids, String keywords,
			String seq, String appVersion) {
		// Building Parameters
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("email", email));
		// params.add(new BasicNameValuePair("password", password));

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(GlobalConst.tokenTag, token));
		params.add(new BasicNameValuePair(GlobalConst.seqTag, seq));
		params.add(new BasicNameValuePair(GlobalConst.appVersionTag, appVersion));
		params.add(new BasicNameValuePair(GlobalConst.uidTag, uids));
		params.add(new BasicNameValuePair(GlobalConst.formatTag, "json"));
		params.add(new BasicNameValuePair(
				GlobalConst.keywordsTag,
				"nickname,sex,birthday,place,desc,mode,some,state,figure,msex,magemin,magemax,mdist"));
		JSONObject jsonResponseObject = jsonParser.makeServiceCall(
				GlobalConst.ServerBaseURL + GlobalConst.getUserConfURL,
				JSONParser.GET, params);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make getUserConfig
	 * 
	 * @param token
	 * @param uids
	 * @param appversion
	 * @return JSONObject
	 * */
	public JSONObject getUserConfig(String token, String uids) {
		// Building Parameters
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("email", email));
		// params.add(new BasicNameValuePair("password", password));

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(GlobalConst.tokenTag, token));
		params.add(new BasicNameValuePair(GlobalConst.uidTag, uids));
		JSONObject jsonResponseObject = jsonParser.makeServiceCall(
				GlobalConst.ServerBaseURL + GlobalConst.getUserConfURL,
				JSONParser.GET, params);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make setUserConfig
	 * 
	 * @param token
	 * @param uids
	 * @param appversion
	 * @param some
	 * @return JSONObject
	 * */
	public JSONObject setUserConfig(String token, String uids, String nickName,String sex,
			String password, String birthday, String place, String gps,
			String devToken, String desc, String mode, String some,
			String status, String figure, String mSex, String mAgeMin,
			String mAgeMax, String mDist, String appVersion, String iOSVersion,
			String coin, String seq) {
		// Building Parameters

		JSONObject object = new JSONObject();
		try {
			JSONObject object2 = new JSONObject();
			object2.put(GlobalConst.rTag, GlobalConst.setUserConfReqTag);
			object2.put(GlobalConst.tokenTag, token);
			object2.put(GlobalConst.uidTag, uids);
			object2.put(GlobalConst.nicknameTag, nickName);
			object2.put(GlobalConst.sexTag, sex);
			object2.put(GlobalConst.passwordTag, password);
			object2.put(GlobalConst.birthdayTag, birthday);
			object2.put(GlobalConst.placeTag, place);
			object2.put(GlobalConst.gpsTag, gps);
			object2.put(GlobalConst.devTokenTag, devToken);
			object2.put(GlobalConst.descTag, desc);
			object2.put(GlobalConst.modeTag, mode);
			object2.put(GlobalConst.someTag, some);
			object2.put(GlobalConst.statusTag, status);
			object2.put(GlobalConst.figureTag, figure);
			object2.put(GlobalConst.mSexTag, mSex);
			object2.put(GlobalConst.mAgeMinTag, mAgeMin);
			object2.put(GlobalConst.mAgeMaxTag, mAgeMax);
			object2.put(GlobalConst.mDistTag, mDist);
			object2.put(GlobalConst.appVersionTag, appVersion);
			object2.put(GlobalConst.iOSVersionTag, iOSVersion);
			object2.put(GlobalConst.coinsTag, coin);
			object2.put(GlobalConst.seqTag, seq);
			object.put(GlobalConst.setUserConfReqTag, object2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponseObject = jsonParser.getJSONFromUrl(
				GlobalConst.ServerBaseURL + GlobalConst.setuserconfURL, object);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;

	}

	/**
	 * function call getDate
	 * 
	 * @param token
	 * @param last
	 * @param limit
	 * @param begin
	 * @param end
	 * @param appVersion
	 * @param format
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject getDate(String token, String last, String limit,
			String begin, String end, String appVersion, String seq) {
		// Building Parameters

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(GlobalConst.tokenTag, token));
		params.add(new BasicNameValuePair(GlobalConst.lastTag, last));
		params.add(new BasicNameValuePair(GlobalConst.limitTag, limit));
		params.add(new BasicNameValuePair(GlobalConst.beginTag, begin));
		params.add(new BasicNameValuePair(GlobalConst.endTag, end));
		params.add(new BasicNameValuePair(GlobalConst.appVersionTag, appVersion));
		params.add(new BasicNameValuePair(GlobalConst.formatTag, "json"));
		params.add(new BasicNameValuePair(GlobalConst.seqTag, seq));

		JSONObject jsonResponseObject = jsonParser.makeServiceCall(
				GlobalConst.ServerBaseURL + GlobalConst.getdateURL,
				JSONParser.GET, params);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;

	}

	/**
	 * function make setDate
	 * 
	 * @param token
	 * @param did
	 * @param state
	 * @param datetime
	 * @param location
	 * @param clue
	 * @param appVersion
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject setDate(String token, String did, String state,
			String datetime, String location, String clue, String appVersion,
			String seq) {
		// Building Parameters

		JSONObject object = new JSONObject();
		try {
			JSONObject object2 = new JSONObject();
			object2.put(GlobalConst.rTag, GlobalConst.setDateReqTag);
			object2.put(GlobalConst.tokenTag, token);
			object2.put(GlobalConst.didTag, did);
			object2.put(GlobalConst.stateTag, state);
			object2.put(GlobalConst.dateTimeTag, datetime);
			object2.put(GlobalConst.locationTag, location);
			object2.put(GlobalConst.clueTag, clue);
			object2.put(GlobalConst.seqTag, seq);
			object2.put(GlobalConst.appVersionTag, appVersion);
			object.put(GlobalConst.setDateReqTag, object2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponseObject = jsonParser.getJSONFromUrl(
				GlobalConst.ServerBaseURL + GlobalConst.setdateURL, object);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;

	}

	/**
	 * function make getMatch
	 * 
	 * @param token
	 * @param did
	 * @param state
	 * @param datetime
	 * @param location
	 * @param clue
	 * @param appVersion
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject getMatch(String token, String mode, String last,
			String limit, String appVersion, String seq) {
		// Building Parameters

		JSONObject object = new JSONObject();
		try {
			JSONObject object2 = new JSONObject();
			object2.put(GlobalConst.rTag, GlobalConst.getMatchReqTag);
			object2.put(GlobalConst.tokenTag, token);
			object2.put(GlobalConst.modeTag, mode);
			object2.put(GlobalConst.lastTag, last);
			object2.put(GlobalConst.limitTag, limit);
			object2.put(GlobalConst.seqTag, seq);
			object2.put(GlobalConst.appVersionTag, appVersion);
			object2.put(GlobalConst.formatTag, "json");
			object.put(GlobalConst.getMatchReqTag, object2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponseObject = jsonParser.getJSONFromUrl(
				GlobalConst.ServerBaseURL + GlobalConst.getMatchURL, object);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;

	}

	/**
	 * function make setMatch
	 * 
	 * @param token
	 * @param uId2
	 * @param like
	 * @param appVersion
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject setMatch(String token, String uID2, String like,
			String appVersion, String seq) {
		// Building Parameters

		JSONObject object = new JSONObject();
		try {
			JSONObject object2 = new JSONObject();
			object2.put(GlobalConst.rTag, GlobalConst.setMatchReqTag);
			object2.put(GlobalConst.tokenTag, token);
			object2.put(GlobalConst.uId2Tag, uID2);
			object2.put(GlobalConst.likeTag, like);
			object2.put(GlobalConst.seqTag, seq);
			object2.put(GlobalConst.appVersionTag, appVersion);
			object.put(GlobalConst.setMatchReqTag, object2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponseObject = jsonParser.getJSONFromUrl(
				GlobalConst.ServerBaseURL + GlobalConst.setMatchURL, object);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;

	}

	/**
	 * function make getOpt
	 * 
	 * @param token
	 * @param screen
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject getOpt(String token, String screen, String seq) {
		// Building Parameters
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("email", email));
		// params.add(new BasicNameValuePair("password", password));

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(GlobalConst.tokenTag, token));
		params.add(new BasicNameValuePair(GlobalConst.screenTag, screen));
		params.add(new BasicNameValuePair(GlobalConst.seqTag, seq));
		JSONObject jsonResponseObject = jsonParser.makeServiceCall(
				GlobalConst.ServerBaseURL + GlobalConst.getOptURL,
				JSONParser.GET, params);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make setOpt
	 * 
	 * @param token
	 * @param screen
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject setOpt(String token, String screen, String seq) {
		// Building Parameters
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("email", email));
		// params.add(new BasicNameValuePair("password", password));

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(GlobalConst.tokenTag, token));
		params.add(new BasicNameValuePair(GlobalConst.screenTag, screen));
		params.add(new BasicNameValuePair(GlobalConst.seqTag, seq));
		JSONObject jsonResponseObject = jsonParser.makeServiceCall(
				GlobalConst.ServerBaseURL + GlobalConst.setOptURL,
				JSONParser.GET, params);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make getNotification
	 * 
	 * @param token
	 * @param mode
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject getNotification(String token, String mode, String seq) {
		// Building Parameters
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("email", email));
		// params.add(new BasicNameValuePair("password", password));

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(GlobalConst.tokenTag, token));
		params.add(new BasicNameValuePair(GlobalConst.modeTag, mode));
		params.add(new BasicNameValuePair(GlobalConst.seqTag, seq));
		JSONObject jsonResponseObject = jsonParser.makeServiceCall(
				GlobalConst.ServerBaseURL + GlobalConst.getNotificationURL,
				JSONParser.GET, params);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

	/**
	 * function make setNotification
	 * 
	 * @param token
	 * @param mode
	 * @param seq
	 * @return JSONObject
	 * */
	public JSONObject setNotification(String token, String nID, String state,
			String seq, String appVersion) {
		// Building Parameters
		JSONObject object = new JSONObject();
		try {
			JSONObject object2 = new JSONObject();
			object2.put(GlobalConst.rTag, GlobalConst.setNotificationReqTag);
			object2.put(GlobalConst.tokenTag, token);
			object2.put(GlobalConst.nIdTag, nID);
			object2.put(GlobalConst.stateTag, state);
			object2.put(GlobalConst.seqTag, seq);
			object2.put(GlobalConst.appVersionTag, appVersion);
			object.put(GlobalConst.setNotificationReqTag, object2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonResponseObject = jsonParser.getJSONFromUrl(
				GlobalConst.ServerBaseURL + GlobalConst.setMatchURL, object);
		Log.e("JSONaaaaaaaaaaaaaaaa", jsonResponseObject.toString());
		return jsonResponseObject;
	}

}
