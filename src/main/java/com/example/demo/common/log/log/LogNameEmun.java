package com.example.demo.common.log.log;

/// <summary>
        /// 当系统出现错误存的文件名
        /// </summary>
        public enum LogNameEmun
        {
            Request_Info,//请求
            /// <summary>
            /// 系统错误
            /// </summary>
            System,//系统
            /// <summary>
            /// 操作数据库
            /// </summary>
            DB_Info,
            /// <summary>
            /// 提醒信息
            /// </summary>
            //Information,
            /// <summary>
            /// 信息
            /// </summary>
            SystemManger,
            /// <summary>
            /// API调用
            /// </summary>
            API_Info,//api调用

            Service_Info//逻辑层
            /// <summary>
            /// API调用错误
            /// </summary>
           // Get_API_Error
;
            public String getLogName(LogNameEmun logName)
            {
                switch (logName)
                {
                    case System:
                        return "系统";
                    case DB_Info:
                        return "操作数据库";
                    case API_Info:
                        return "API调用";
                    case Service_Info:
                        return "逻辑层";
                    case Request_Info:
                        return "请求";
                    default:
                        return "未知异常";
                }
            }
        }