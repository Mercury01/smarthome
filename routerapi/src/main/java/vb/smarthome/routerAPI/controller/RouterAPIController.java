package vb.smarthome.routerAPI.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import vb.smarthome.routerAPI.driver.ClientDriver;
import vb.smarthome.routerAPI.entity.DHCPClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RouterAPIController {

	@Autowired
	private ClientDriver clientDriver;

	@Autowired
	private ObjectMapper objectMapper;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/getClients", method = RequestMethod.GET)
	public @ResponseBody List<DHCPClient> getHomePage() {
		try {
			List<DHCPClient> dhcpClientList = clientDriver.getDHCPClientList();
			return dhcpClientList;
		} catch (Exception e) {
			logger.error("Unable to get DHCP client list", e);
			return null;
		}
	}

	// @Autowired
	// private DbDao dbDao;
	//
	// @Autowired
	// private FacebookCalendar facebookApi;
	//
	// @Autowired
	// private GoogleCalendar googleApi;
	//
	// @Autowired
	// private GooglePlacesAPI googlePlacesAPI;
	//
	// @Autowired
	// private InstagramAPI instagramAPI;
	//
	// @Autowired
	// private Reminder reminder;
	//
	// @Autowired
	// private ObjectMapper objectMapper;
	//
	// @Autowired
	// private ICloudCalendar iCloudService;
	//
	// @Autowired
	// private HolidayCalendar holidayCalendar;
	//
	// @Autowired
	// private LocalCalendar localCalendar;
	//
	// @Autowired
	// private ConnectionRepository connectionRepository;
	//
	// @Autowired
	// private LdapDao ldapDao;
	//
	// private DateFormat df;
	//
	// public CalendarController() {
	// super();
	// df = new SimpleDateFormat();
	// df.setTimeZone(TimeZone.getTimeZone("GMT"));
	// }
	//
	// @RequestMapping(value = "/home", method = RequestMethod.GET)
	// public String getHomePage(Model model, Principal principal, TimeZone
	// timeZone) {
	// return getHomePageInitial(model, principal);
	// }
	//
	// @RequestMapping(value = "/", method = RequestMethod.GET)
	// public String getHomePageInitial(Model model, Principal principal) {
	//
	// if (!checkConnection(EventProvider.FACEBOOK)) {
	// model.addAttribute("facebook", false);
	// }
	// if (!checkConnection(EventProvider.GOOGLE)) {
	// model.addAttribute("google", false);
	// }
	//
	// LdapUser user = ldapDao.getUserByUsername(principal.getName());
	//
	// model.addAttribute("userPreference", user.getUserPreferences());
	//
	// return "index";
	// }
	//
	// //Usernames param looks like shit, but it only works this way
	// @RequestMapping(value = "/getEventsForUsers", method =
	// RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	// @ResponseBody
	// /**
	// * Called with ajax
	// * @param principal
	// * @return
	// */
	// public String getEventsForUsers(@RequestParam(value="usernames[]",
	// required=false) String[] usernames,
	// @RequestParam long start, @RequestParam long end, TimeZone timeZone) {
	//
	// if(usernames == null || usernames.length == 0) {
	// return null;
	// }
	//
	// Set<Event> result = new HashSet<Event>();
	//
	// for(EventProvider provider : EventProvider.values()) {
	// for (String username : usernames) {
	// List<Event> events = null;
	// try
	// {
	// events = fetchEvents(provider, start, end, username, timeZone);
	// } catch (Exception e)
	// {
	// logger.warn("Exception during fetching " + username + "'s " + provider +
	// "events", e);
	// }
	// if (events != null)
	// {
	// result.addAll(events);
	// }
	// }
	// }
	//
	// try {
	// return objectMapper.writeValueAsString(result);
	// } catch (IOException e) {
	// logger.error("Unable to get events for users: " + usernames, e);
	// return null;
	// }
	// }
	//
	// @RequestMapping(value = "/getEvents/{eventProvider}", method =
	// RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	// @ResponseBody
	// /**
	// * Called with ajax
	// * @param principal
	// * @return
	// */
	// public String getEvents(@PathVariable EventProvider eventProvider,
	// @RequestParam long start,
	// @RequestParam long end, @RequestParam(defaultValue="") String user,
	// TimeZone timeZone) {
	//
	// List<Event> events = fetchEvents(eventProvider, start, end, user,
	// timeZone);
	// try {
	// return objectMapper.writeValueAsString(events);
	// } catch (IOException e) {
	// logger.error("JSON Exception while getting events", e);
	// return null;
	// }
	// }
	//
	// private List<Event> fetchEvents(EventProvider eventProvider, long start,
	// long end, String user, TimeZone timeZone) {
	// List<Event> events = null;
	//
	// // index page ""-t kuld userkent
	// if (!StringUtils.isEmpty(user))
	// {
	// LdapUser ldapUser = ldapDao.getUserByUsername(user);
	// if (ldapUser == null)
	// return null;
	// }
	//
	// start = TimeZoneConverter.toUTC(new Date(start), timeZone).getTime();
	// end = TimeZoneConverter.toUTC(new Date(end), timeZone).getTime();
	//
	// try {
	// switch (eventProvider) {
	// case LOCAL:
	// try {
	// events = localCalendar.getEventsOfInterval(start, end, user, timeZone);
	// } catch (Exception e) {
	// logger.error("Database Exception while getting events", e);
	// }
	// break;
	// case FACEBOOK:
	// try {
	// events = facebookApi.getEventsOfInterval(start, end, user, timeZone);
	// } catch (SocialException e) {
	// logger.error("Social Exception while getting Facebook events: " +
	// e.getMessage());
	// }
	// break;
	// case GOOGLE:
	// try {
	// events = googleApi.getEventsOfInterval(start, end, user, timeZone);
	// } catch (Exception e) {
	// logger.error("Exception while getting Google events: " + e.getMessage(),
	// e);
	// }
	// break;
	// case ICLOUD:
	// events = iCloudService.getEventsOfInterval(start, end, user, timeZone);
	// break;
	// case HOLIDAY:
	// events = holidayCalendar.getEventsOfInterval(start, end, user, timeZone);
	// break;
	// }
	// } catch (Exception e) {
	// logger.error("An error occured while fetching events for parameters: " +
	// user + " " + start + " " + end + " " + eventProvider, e);
	// }
	// return events;
	// }
	//
	// @RequestMapping(value = "/getUsers", method = RequestMethod.GET, produces
	// = "text/plain;charset=UTF-8")
	// @ResponseStatus(value = HttpStatus.OK)
	// public @ResponseBody String getUsers(String term) {
	// try {
	// List<LdapUser> searchForUsers = ldapDao.searchForUsers(term);
	// return objectMapper.writeValueAsString(searchForUsers);
	// } catch (Exception e) {
	// logger.error("Unable to search for users with term: " + term, e);
	// return null;
	// }
	// }
	//
	// public boolean checkConnection(EventProvider eventProvider) {
	// try {
	// switch (eventProvider) {
	// case FACEBOOK:
	// return connectionRepository.getPrimaryConnection(Facebook.class) != null;
	// case GOOGLE:
	// return connectionRepository.getPrimaryConnection(Google.class) != null;
	// default:
	// return true;
	// }
	// } catch (Exception e) {
	// logger.error("An error occured while checking connection for provider: "
	// + eventProvider, e);
	// return false;
	// }
	// }
	//
	// // https://github.com/devbridge/jQuery-Autocomplete jQuery Ajax
	// autocomplete
	// @RequestMapping(value = "/getGooglePlaces", method = RequestMethod.GET,
	// produces = "text/plain; charset=UTF-8")
	// @ResponseBody
	// public String getGooglePlaces(String searchText, String lat, String lng,
	// int radius) {
	//
	// Location location = new Location(lng, lat);
	//
	// List<GooglePlace> places =
	// googlePlacesAPI.getGooglePlacesByText(searchText, location, radius);
	//
	// try {
	// String result = objectMapper.writeValueAsString(places);
	// return result;
	// } catch (Exception e) {
	// logger.error("Exception", e);
	// return null;
	// }
	// }
	//
	// @RequestMapping(value = "/getGooglePlaceDetails", method =
	// RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	// @ResponseBody
	// public String getGooglePlaceDetails(String googlePlaceId)
	// {
	// GooglePlace detailsOfPlace =
	// googlePlacesAPI.getDetailsOfPlace(googlePlaceId);
	//
	// try
	// {
	// if(detailsOfPlace.getPhoto() != null)
	// {
	// // https://maps.googleapis.com/maps/api/place/photo?parameters
	// for (Photo item : detailsOfPlace.getPhoto())
	// {
	// String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?";
	// photoUrl += "key=" + PropertyHandler.getProperty("google.api.key",
	// Config.GOOGLE);
	// photoUrl += "&photoreference=" + item.getNormalPhotoReference();
	//
	//
	// item.setNormalPhotoReference(photoUrl + "&maxheight=1600");
	// item.setThumbnailPhotoReference(photoUrl + "&maxheight=120");
	// }
	// }
	//
	// if(detailsOfPlace.getReview() != null)
	// {
	// for (Review item : detailsOfPlace.getReview())
	// {
	// Date date = new Date(Long.parseLong(item.getTime()) * 1000);
	// SimpleDateFormat dateFormat = new
	// SimpleDateFormat(DetailedEventForm.DATE_TIME_PATTERN);
	// String formattedDate = dateFormat.format(date);
	// item.setTime(formattedDate);
	// }
	// }
	//
	// String result = objectMapper.writeValueAsString(detailsOfPlace);
	// logger.debug(result);
	//
	// return result;
	// } catch (Exception e)
	// {
	// logger.error("Exception", e);
	// return null;
	// }
	// }
	//
	// @RequestMapping(value = "/getInstagramImages", method =
	// RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	// @ResponseBody
	// public String getInstagramImages(String lat, String lng) {
	// String result = null;
	// try {
	// List<Image> imagesOfLocation = instagramAPI.getImagesOfLocation(lat,
	// lng);
	// result = objectMapper.writeValueAsString(imagesOfLocation);
	// } catch (Exception e) {
	// logger.error("Unable to get Instagram images", e);
	// }
	// return result;
	// }
}
