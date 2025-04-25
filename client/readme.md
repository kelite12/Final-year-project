# 请求不带参数
RetrofitTools.post("getAllNews", News::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                //获取到结果集是集合
                var news = succ as List<News>;

            }

            override fun failure(msg: String) {
            }

        })

     RetrofitTools.post("getNews", News::class.java,object :RetrofitTools.IRetrofitResponse{
                    override fun <T> success(succ: T) {
                        //获取到结果集是对象
                        var news = succ as News;

                    }

                    override fun failure(msg: String) {
                    }

                })
 # 请求带参数
 var map = HashMap<String,String>();
        map.put("username","admin");
        map.put("password","admin");
        RetrofitTools.post("login",map, User::class.java,object :RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                var user = succ as User;

            }

            override fun failure(msg: String) {
            }

        })

# 含有图片上传的提交数据
 RetrofitTools.upload("insertFornum",map,parts,object:RetrofitTools.IRetrofitResponse{
            override fun <T> success(succ: T) {
                if("true".equals(succ.toString())){
                    toast("添加成功")
                    finish();
                }else{
                    toast("添加失败")
                }
            }
            override fun failure(msg: String) {
                toast("网络异常")
            }
        })