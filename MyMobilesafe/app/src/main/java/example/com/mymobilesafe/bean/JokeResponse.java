package example.com.mymobilesafe.bean;

import java.util.List;

/**
 * Created by zyp on 8/22/17.
 */

public class JokeResponse {

    /**
     * status : 0
     * msg : ok
     * result : {"total":"51693","pagenum":"1","pagesize":"1","list":[{"content":"","addtime":"2017-08-22 03:20:13","url":""}]}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * total : 51693
         * pagenum : 1
         * pagesize : 1
         * list : [{"content":"","addtime":"2017-08-22 03:20:13","url":""}]
         */

        private String total;
        private String pagenum;
        private String pagesize;
        private List<ListBean> list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPagenum() {
            return pagenum;
        }

        public void setPagenum(String pagenum) {
            this.pagenum = pagenum;
        }

        public String getPagesize() {
            return pagesize;
        }

        public void setPagesize(String pagesize) {
            this.pagesize = pagesize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * content : 大学时，有一天晚上，我们寝室的都睡了，听到对面女寝有哭声，我一哥们被嘈醒他大声吼道，哭毛啊要是失恋了，明天哥做你男人！那女生听到后就不哭了，第二天，那女的亲自来我们寝室要人。此后我们整个宿舍楼到晚上，全部竖起了耳朵。
             * addtime : 2017-08-22 03:20:13
             * url : http://m.kaixinhui.com/detail-56502.html
             */

            private String content;
            private String addtime;
            private String url;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
