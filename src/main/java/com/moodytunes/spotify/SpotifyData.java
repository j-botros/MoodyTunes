package com.moodytunes.spotify;

import com.google.gson.annotations.SerializedName;

public class SpotifyData {
    public static class UserAccessData {
        public String access_token;
        public String token_type;
        public String scope;
        public int expires_in;
        public String refresh_token;
    }

    public static class ClientAccessData {
        public String access_token;
        public String token_type;
        public int expires_in;
    }

    public static class UserData {
        public String country;
        public String display_name;
        public String email;
        public ExplicitContent explicit_content;
        public ExternalUrls external_urls;
        public Followers followers;
        public String href;
        public String id;
        public ImageObject[] images;
        public String product;
        public String type;
        public String uri;

        public static class ExplicitContent {
            public boolean filter_enabled;
            public boolean filter_locked;
        }

        public static class ExternalUrls {
            public String spotify;
        }

        public static class Followers {
            public String href;
            public int total;
        }

        public static class ImageObject {
            public String url;
            public int height;
            public int width;
        }
    }

    public static class PlaylistData {
        public boolean collaborative;
        public String description;
        public ExternalUrls external_urls;
        public String href;
        public String id;
        public ImageObject[] images;
        public String name;
        public Owner owner;
        @SerializedName("public")
        public Boolean public_;
        public String snapshot_id;
        public Tracks tracks;
        public String type;
        public String uri;

        public static class Tracks {
            public String href;
            public int limit;
            public String next;
            public int offset;
            public String previous;
            public int total;
            public PlaylistTrackObject[] items;

            public static class PlaylistTrackObject {
                public String added_at;
                public AddedBy added_by;
                public boolean is_local;
                public TrackObject track;

                public static class AddedBy {
                    public ExternalUrls external_urls;
                    public String href;
                    public String id;
                    public String type;
                    public String uri;
                }
            }
        }
    }

    public static class Owner {
        public ExternalUrls external_urls;
        public String href;
        public String id;
        public String type;
        public String uri;
        public String display_name;
    }

    public static class ImageObject {
        public String url;
        public int height;
        public int width;
    }

    public static class ExternalUrls {
        public String spotify;
    }

    public static class UserTopItems {
        public String href;
        public int limit;
        public String next;
        public int offset;
        public String previous;
        public int total;
        public TrackObject[] items;
    }

    public static class TrackObject {
        public Album album;
        public SimplifiedArtistsObject[] artists;
        public String[] available_markets;
        public int disc_number;
        public int duration_ms;
        public boolean explicit;
        public ExternalIds external_ids;
        public ExternalUrls external_urls;
        public String href;
        public String id;
        public boolean is_playable;
        public LinkedFrom linked_from;
        public Restrictions restrictions;
        public String name;
        public int popularity;
        public String preview_url;
        public int track_number;
        public String type;
        public String uri;
        public boolean is_local;
    }
                    
    public static class Album {
        public String album_type;
        public int total_tracks;
        public String[] available_markets;
        public ExternalUrls external_urls;
        public String href;
        public String id;
        public ImageObject[] images;
        public String name;
        public String release_date;
        public String release_date_precision;
        public Restrictions restrictions;
        public String type;
        public String uri;
        public SimplifiedArtistsObject[] artists;
    }

    public static class Restrictions {
        public String reason;
    }

    public static class SimplifiedArtistsObject {
        public ExternalUrls external_urls;
        public String href;
        public String id;
        public String name;
        public String type;
        public String uri;
    }

    public static class ExternalIds {
        public String isrc;
        public String ean;
        public String upc;
    }

    public static class LinkedFrom { }

    public static class RecommendedSongs {
        public TrackObject[] tracks;
    }
}