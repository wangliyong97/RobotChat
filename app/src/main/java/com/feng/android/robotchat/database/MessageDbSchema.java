package com.feng.android.robotchat.database;

public class MessageDbSchema {

    public static final class RecordTable{

        public static final String NAME = "records";

        public static final class Cols{
            public static final String ID = "id";
            public static final String UUID = "uuid";
            public static final String TIME = "time";
            public static final String TYPE = "type";
            public static final String TEXT = "text";
            public static final String IMAGE = "image";
            public static final String CODE = "code";
            public static final String COMMENT = "comment";
            public static final String URL = "url";

        }
    }

    public static final class CollectionTable{

        public static final String NAME = "collections";

        public static final class Cols{
            public static final String ID = "id";
            public static final String UUID = "uuid";
            public static final String TIME = "time";
            public static final String TYPE = "type";
            public static final String CONTENT = "content";
            public static final String IMAGE = "image";
            public static final String COMMENT = "comment";
        }
    }

}
