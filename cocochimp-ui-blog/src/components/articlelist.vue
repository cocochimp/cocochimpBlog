<!-- 文章列表 -->
<template>
  <el-row class="sharelistBox">
    <el-col
      :span="24"
      class="s-item activeRender tcommonBox"
      v-for="(item, index) in articleList"
      :key="'article' + index"
    >
      <a :href="'#/DetailArticle?aid=' + item.id" target="_blank">
        <div class="flag">
          <span class="s-round-date">
            <span class="month" v-html="showInitDate(item.createTime, 'month') + '月'"></span>
            <span class="day" v-html="showInitDate(item.createTime, 'date')"></span>
          </span>

          <div class="article-content" :class="index % 2 == 0 ? 'article-even' : 'article-odd'" style="position: relative" >
            <div>
              <header style=" display: flex; flex-direction: column; justify-content: start; align-items: start;">
                <h1>
                  <a :href="'#/DetailArticle?aid=' + item.id" target="_blank">
                    {{ item.title }}
                  </a>
                </h1>
                <h2>
                  <i class="fa fa-fw fa-user"></i>发表于
                  <i class="fa fa-fw fa-clock-o"></i
                  ><span v-html="showInitDate(item.createTime, 'all')"></span> •
                  <i class="fa fa-fw fa-eye"></i>{{ item.viewCount }} 次围观 •
                </h2>
              </header>
              <p>
                {{ item.summary }}
              </p>
            </div>

            <img
              :class="index % 2 == 0 ? '' : 'title-even'"
              v-lazy="item.thumbnail"
              alt=""
              class="maxW"
            />
          </div>

          <!-- <div class="viewdetail">
            <a class="tcolors-bg" :href="'#/DetailArticle?aid='+item.id" target="_blank">
                    阅读全文>>
                </a>
          </div> -->
        </div>
        <div class="ui label" style="margin-top: 20px">
          <a :href="'#/Share?classId=' + item.class_id">
            {{item.categoryName}}
          </a>
        </div>
      </a>
    </el-col>
    <el-col class="viewmore">
      <a
        v-show="hasMore"
        class="tcolors-bg"
        href="javascript:void(0);"
        @click="addMoreFun"
        >点击加载更多</a
      >
      <a v-show="!hasMore" class="tcolors-bg" href="javascript:void(0);"
        >暂无更多数据</a
      >
    </el-col>
  </el-row>
</template>

<script>
import { initDate } from "../utils/server.js";
import { articleList } from "../api/article";
export default {
  name: "Share",
  data() {
    //选项 / 数据
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: 0,
      },
      articleList: [],
      hasMore: true,
    };
  },

  methods: {
    //事件处理器
    showInitDate: function (oldDate, full) {
      return initDate(oldDate, full);
    },
    getList() {
      articleList(this.queryParams).then((response) => {
        this.articleList = this.articleList.concat(response.rows);
        if (response.total <= this.articleList.length) {
          this.hasMore = false;
        } else {
          this.hasMore = true;
          this.queryParams.pageNum++;
        }
      });
    },
    showSearchShowList: function (initData) {
      //展示数据
      if (initData) {
        this.articleList = [];
      }
      this.getList();
    },
    addMoreFun: function () {
      //查看更多
      this.showSearchShowList(false);
    },
    routeChange: function () {
      var that = this;
      this.queryParams.categoryId =
        that.$route.query.classId == undefined
          ? 0
          : parseInt(that.$route.query.classId); //获取传参的classId
      this.showSearchShowList(true);
    },
  },
  components: {
    //定义组件
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    $route: "routeChange",
    "$store.state.keywords": "routeChange",
  },
  created() {
    //生命周期函数
    // console.log(this.$route);
    var that = this;
    that.routeChange();
  },
};
</script>

<style lang="less" scoped>
.labelflag {
}
.flag {
  display: flex;
  flex-direction: column-reverse;
}
.title-even {
  //  margin-right: -100px;
}
.activeRender:hover {
  background-color: rgba(230, 244, 250, 0.5);
}
.maxW:hover {
  transition: all 0.1s ease-in-out;
  transform: translateY(-10px);
  transform: scale(1.05);
}

.maxW {
  width: 50%;
  height: 250px;
}
.article-content {
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.article-odd {
  flex-direction: row-reverse;
}
.article-even {
}
/*分享标题*/
.shareTitle {
  margin-bottom: 40px;
  position: relative;
  border-radius: 5px;
  background: #fff;
  padding: 15px;
}
.shareclassTwo {
  width: 100%;
}
.shareclassTwo li {
  display: inline-block;
}
.shareclassTwo li a {
  display: inline-block;
  padding: 3px 7px;
  margin: 5px 10px;
  color: #fff;
  border-radius: 4px;
  background: #64609e;
  border: 1px solid #64609e;
  transition: transform 0.2s linear;
  -webkit-transition: transform 0.2s linear;
}
.shareclassTwo li a:hover {
  transform: translate(0, -3px);
  -webkit-transform: translate(0, -3px);
}
.shareclassTwo li a.active {
  background: #fff;
  color: #64609e;
}
/*文章列表*/
.sharelistBox {
  transition: all 0.5s ease-out;
  font-size: 15px;
}

img[lazy="loading"] {
  width: 300px;
  height: 300px;
}

/*.sharelistBox .viewmore a:hover,.s-item .viewdetail a:hover{
        background: #48456C;
    }*/
</style>
