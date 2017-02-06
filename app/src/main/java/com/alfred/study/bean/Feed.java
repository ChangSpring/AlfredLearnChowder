package com.alfred.study.bean;

import java.util.List;

/**
 * Created by Alfred on 2016/12/15.
 */

public class Feed {

    /**
     * following : 0
     * music : {"artist":"阿悄","artistId":"5400","lrc":"http://static.yximgs.com/udata/music/NnSJDLPeEYE_bgm.trcx","image":"http://img3.kuwo
     * .cn/star/albumcover/500/82/8/1197415569.jpg","chorus":57034,"avatarUrl":"http://static.yximgs.com/s1/music/defaultAvatar.png","auditionUrl":"http://m
     * .kuwo.cn/?mid=MUSIC_3433442&bdfrom=ks","duration":213,"name":"治愈糖","id":3433442,"type":1}
     * comments : []
     * caption : ☺️☺️
     * magicFace : {"image":"http://static.yximgs.com/udata/music/magic_face_img_vxw9hHbSSFg","version":8,"name":"素颜","resource":"http://static.yximgs
     * .com/udata/music/magic_face_res_vIQk4URcGAQ","id":258}
     * hasMagicFaceTag : true
     * verified : false
     * time : 2016-12-19 14:40:00
     * timestamp : 1482129600603
     * type : 1
     * tags : []
     * user_id : 224256557
     * comment_count : 50
     * photo_id : 1388977333
     * exp_tag : 1_i/1554204689430646784_h86
     * user_sex : F
     * photo_status : 0
     * us_m : 0
     * view_count : 36058
     * like_count : 673
     * unlike_count : 0
     * forward_count : 0
     * ext_params : {"mtype":3,"color":"449f73","w":480,"sound":15255,"h":640,"interval":20,"video":15200}
     * tag_hash_type : 1
     * headurls : [{"cdn":"js.a.yximgs.com","url":"http://js.a.yximgs.com/uhead/AB/2016/11/23/01/BMjAxNjExMjMwMTU0MzhfMjI0MjU2NTU3XzFfaGQ0OQ==.jpg",
     * "urlPattern":"http://js.a.yximgs.com/uhead/AB/2016/11/23/01/BMjAxNjExMjMwMTU0MzhfMjI0MjU2NTU3XzFfaGQ0OQ==
     * .jpg@base@tag%3DimgScale%26r%3D0%26q%3D85%26w%3D{w}%26h%3D{h}%26rotate"},{"cdn":"ali2.a.yximgs.com","url":"http://ali2.a.yximgs
     * .com/uhead/AB/2016/11/23/01/BMjAxNjExMjMwMTU0MzhfMjI0MjU2NTU3XzFfaGQ0OQ==.jpg","urlPattern":"http://aliimg.a.yximgs
     * .com/uhead/AB/2016/11/23/01/BMjAxNjExMjMwMTU0MzhfMjI0MjU2NTU3XzFfaGQ0OQ==.jpg@0e_0o_0l_{h}h_{w}w_85q.src"}]
     * main_mv_urls : [{"cdn":"js.a.yximgs.com","url":"http://js.a.yximgs.com/upic/2016/12/19/14/BMjAxNjEyMTkxNDM5NTlfMjI0MjU2NTU3XzEzODg5NzczMzNfMV8z
     * .mp4?tag=1-1482205094-h-0-kv0eferneu-6529da3fe31887d2"},{"cdn":"tx2.a.yximgs.com","url":"http://tx2.a.yximgs
     * .com/upic/2016/12/19/14/BMjAxNjEyMTkxNDM5NTlfMjI0MjU2NTU3XzEzODg5NzczMzNfMV8z.mp4?tag=1-1482205094-h-1-iafghtucnw-d9dda369688daa85"}]
     * cover_thumbnail_urls : [{"cdn":"js.a.yximgs.com","url":"http://js.a.yximgs
     * .com/upic/2016/12/19/14/BMjAxNjEyMTkxNDM5NTlfMjI0MjU2NTU3XzEzODg5NzczMzNfMV8z_low.webp?tag=1-1482205094-h-0-oje7akwelq-1b39103d6bca4d32"},{"cdn":"tx2
     * .a.yximgs.com","url":"http://tx2.a.yximgs.com/upic/2016/12/19/14/BMjAxNjEyMTkxNDM5NTlfMjI0MjU2NTU3XzEzODg5NzczMzNfMV8z_low
     * .webp?tag=1-1482205094-h-1-esm4uvkqmx-9dd60518a7a0880e"}]
     * us_c : 0
     * us_d : 1
     * forward_stats_params : {"et":"1_i/1554204689430646784_h86"}
     * user_name : 宁夏💋晨宝儿
     * liked : 0
     * reco_reason : h86
     */

    private int following;
    private MusicBean music;
    private String caption;
    private MagicFaceBean magicFace;
    private boolean hasMagicFaceTag;
    private boolean verified;
    private String time;
    private long timestamp;
    private int type;
    private int user_id;
    private int comment_count;
    private int photo_id;
    private String exp_tag;
    private String user_sex;
    private int photo_status;
    private int us_m;
    private int view_count;
    private int like_count;
    private int unlike_count;
    private int forward_count;
    private ExtParamsBean ext_params;
    private int tag_hash_type;
    private int us_c;
    private int us_d;
    private ForwardStatsParamsBean forward_stats_params;
    private String user_name;
    private int liked;
    private String reco_reason;
    private List<?> comments;
    private List<?> tags;
    private List<HeadurlsBean> headurls;
    private List<MainMvUrlsBean> main_mv_urls;
    private List<CoverThumbnailUrlsBean> cover_thumbnail_urls;

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public MusicBean getMusic() {
        return music;
    }

    public void setMusic(MusicBean music) {
        this.music = music;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public MagicFaceBean getMagicFace() {
        return magicFace;
    }

    public void setMagicFace(MagicFaceBean magicFace) {
        this.magicFace = magicFace;
    }

    public boolean isHasMagicFaceTag() {
        return hasMagicFaceTag;
    }

    public void setHasMagicFaceTag(boolean hasMagicFaceTag) {
        this.hasMagicFaceTag = hasMagicFaceTag;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getExp_tag() {
        return exp_tag;
    }

    public void setExp_tag(String exp_tag) {
        this.exp_tag = exp_tag;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public int getPhoto_status() {
        return photo_status;
    }

    public void setPhoto_status(int photo_status) {
        this.photo_status = photo_status;
    }

    public int getUs_m() {
        return us_m;
    }

    public void setUs_m(int us_m) {
        this.us_m = us_m;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getUnlike_count() {
        return unlike_count;
    }

    public void setUnlike_count(int unlike_count) {
        this.unlike_count = unlike_count;
    }

    public int getForward_count() {
        return forward_count;
    }

    public void setForward_count(int forward_count) {
        this.forward_count = forward_count;
    }

    public ExtParamsBean getExt_params() {
        return ext_params;
    }

    public void setExt_params(ExtParamsBean ext_params) {
        this.ext_params = ext_params;
    }

    public int getTag_hash_type() {
        return tag_hash_type;
    }

    public void setTag_hash_type(int tag_hash_type) {
        this.tag_hash_type = tag_hash_type;
    }

    public int getUs_c() {
        return us_c;
    }

    public void setUs_c(int us_c) {
        this.us_c = us_c;
    }

    public int getUs_d() {
        return us_d;
    }

    public void setUs_d(int us_d) {
        this.us_d = us_d;
    }

    public ForwardStatsParamsBean getForward_stats_params() {
        return forward_stats_params;
    }

    public void setForward_stats_params(ForwardStatsParamsBean forward_stats_params) {
        this.forward_stats_params = forward_stats_params;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getReco_reason() {
        return reco_reason;
    }

    public void setReco_reason(String reco_reason) {
        this.reco_reason = reco_reason;
    }

    public List<?> getComments() {
        return comments;
    }

    public void setComments(List<?> comments) {
        this.comments = comments;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }

    public List<HeadurlsBean> getHeadurls() {
        return headurls;
    }

    public void setHeadurls(List<HeadurlsBean> headurls) {
        this.headurls = headurls;
    }

    public List<MainMvUrlsBean> getMain_mv_urls() {
        return main_mv_urls;
    }

    public void setMain_mv_urls(List<MainMvUrlsBean> main_mv_urls) {
        this.main_mv_urls = main_mv_urls;
    }

    public List<CoverThumbnailUrlsBean> getCover_thumbnail_urls() {
        return cover_thumbnail_urls;
    }

    public void setCover_thumbnail_urls(List<CoverThumbnailUrlsBean> cover_thumbnail_urls) {
        this.cover_thumbnail_urls = cover_thumbnail_urls;
    }

    public static class MusicBean {
        /**
         * artist : 阿悄
         * artistId : 5400
         * lrc : http://static.yximgs.com/udata/music/NnSJDLPeEYE_bgm.trcx
         * image : http://img3.kuwo.cn/star/albumcover/500/82/8/1197415569.jpg
         * chorus : 57034
         * avatarUrl : http://static.yximgs.com/s1/music/defaultAvatar.png
         * auditionUrl : http://m.kuwo.cn/?mid=MUSIC_3433442&bdfrom=ks
         * duration : 213
         * name : 治愈糖
         * id : 3433442
         * type : 1
         */

        private String artist;
        private String artistId;
        private String lrc;
        private String image;
        private int chorus;
        private String avatarUrl;
        private String auditionUrl;
        private int duration;
        private String name;
        private int id;
        private int type;

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getArtistId() {
            return artistId;
        }

        public void setArtistId(String artistId) {
            this.artistId = artistId;
        }

        public String getLrc() {
            return lrc;
        }

        public void setLrc(String lrc) {
            this.lrc = lrc;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getChorus() {
            return chorus;
        }

        public void setChorus(int chorus) {
            this.chorus = chorus;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getAuditionUrl() {
            return auditionUrl;
        }

        public void setAuditionUrl(String auditionUrl) {
            this.auditionUrl = auditionUrl;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class MagicFaceBean {
        /**
         * image : http://static.yximgs.com/udata/music/magic_face_img_vxw9hHbSSFg
         * version : 8
         * name : 素颜
         * resource : http://static.yximgs.com/udata/music/magic_face_res_vIQk4URcGAQ
         * id : 258
         */

        private String image;
        private int version;
        private String name;
        private String resource;
        private int id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class ExtParamsBean {
        /**
         * mtype : 3
         * color : 449f73
         * w : 480
         * sound : 15255
         * h : 640
         * interval : 20
         * video : 15200
         */

        private int mtype;
        private String color;
        private int w;
        private int sound;
        private int h;
        private int interval;
        private int video;

        public int getMtype() {
            return mtype;
        }

        public void setMtype(int mtype) {
            this.mtype = mtype;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public int getSound() {
            return sound;
        }

        public void setSound(int sound) {
            this.sound = sound;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getVideo() {
            return video;
        }

        public void setVideo(int video) {
            this.video = video;
        }
    }

    public static class ForwardStatsParamsBean {
        /**
         * et : 1_i/1554204689430646784_h86
         */

        private String et;

        public String getEt() {
            return et;
        }

        public void setEt(String et) {
            this.et = et;
        }
    }

    public static class HeadurlsBean {
        /**
         * cdn : js.a.yximgs.com
         * url : http://js.a.yximgs.com/uhead/AB/2016/11/23/01/BMjAxNjExMjMwMTU0MzhfMjI0MjU2NTU3XzFfaGQ0OQ==.jpg
         * urlPattern : http://js.a.yximgs.com/uhead/AB/2016/11/23/01/BMjAxNjExMjMwMTU0MzhfMjI0MjU2NTU3XzFfaGQ0OQ==.jpg@base@tag%3DimgScale%26r%3D0%26q%3D85%26w%3D{w}%26h%3D{h}%26rotate
         */

        private String cdn;
        private String url;
        private String urlPattern;

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlPattern() {
            return urlPattern;
        }

        public void setUrlPattern(String urlPattern) {
            this.urlPattern = urlPattern;
        }
    }

    public static class MainMvUrlsBean {
        /**
         * cdn : js.a.yximgs.com
         * url : http://js.a.yximgs.com/upic/2016/12/19/14/BMjAxNjEyMTkxNDM5NTlfMjI0MjU2NTU3XzEzODg5NzczMzNfMV8z.mp4?tag=1-1482205094-h-0-kv0eferneu-6529da3fe31887d2
         */

        private String cdn;
        private String url;

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class CoverThumbnailUrlsBean {
        /**
         * cdn : js.a.yximgs.com
         * url : http://js.a.yximgs.com/upic/2016/12/19/14/BMjAxNjEyMTkxNDM5NTlfMjI0MjU2NTU3XzEzODg5NzczMzNfMV8z_low.webp?tag=1-1482205094-h-0-oje7akwelq-1b39103d6bca4d32
         */

        private String cdn;
        private String url;

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
