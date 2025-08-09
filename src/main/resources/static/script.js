const CLIENT_ID = "a7b61bd772ec447892f30d14e4cff991";
const REDIRECT_URI = "https://moodytunes-xqx9.onrender.com/callback"

function redirectToSpotify() {
    const responseType = "code";
    const scope = "user-top-read user-read-private playlist-modify-public";

    const authUrl = "https://accounts.spotify.com/authorize?" +
        "response_type=" + responseType +
        "&client_id=" + CLIENT_ID +
        "&scope=" + encodeURIComponent(scope) +
        "&redirect_uri=" + encodeURIComponent(REDIRECT_URI)
    ;

    console.log(authUrl);
    
    window.location.href = authUrl;
}
