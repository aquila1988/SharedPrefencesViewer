package com.aquila.sp.viewer.utils;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class CLog {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";

    private static final String PARAM = "Param";
    private static final String NULL = "null";
    private static final String TAG_DEFAULT = "CLog_";
    private static final String SUFFIX = ".java";

    public static final int JSON_INDENT = 4;

    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;

    private static final int JSON = 0x7;

    private static final int SYSO = 0x8;

    private static final int STACK_TRACE_INDEX_5 = 5;
    private static final int STACK_TRACE_INDEX_4 = 4;
    private static final int MAX_LENGTH = 4000;

    private static boolean IS_SHOW_LOG = true;


    public static void setShowLog(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }


    public static void v(Object... msg) {
        printLog(V, msg);
    }

    public static void d(Object... msg) {
        printLog(D, msg);
    }

    public static void i(Object... msg) {
        printLog(I, msg);
    }

    public static void w(Object... msg) {
        printLog(W, msg);
    }

    public static void e(Object... msg) {
        printLog(E, msg);
    }

    public static void syso(String text) {
        printLog(SYSO, text);
    }

    public static void a(Object... msg) {
        printLog(A, msg);
    }

    public static void debug(Object... msg) {
        printDebug(msg);
    }

    public static void json(String jsonFormat) {
        printLog(JSON, jsonFormat);
    }


    public static void trace() {
        printStackTrace();
    }

    private static void printStackTrace() {

        if (!IS_SHOW_LOG) {
            return;
        }

        Throwable tr = new Throwable();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        String message = sw.toString();

        String traceString[] = message.split("\\n\\t");
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (String trace : traceString) {
            if (trace.contains("at com.socks.library.KLog")) {
                continue;
            }
            sb.append(trace).append("\n");
        }


        String[] contents = wrapperContent(STACK_TRACE_INDEX_4, sb.toString());
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        printDefault(D, tag, headString + msg);
    }

    private static void printLog(int type, Object... objects) {
        if (!IS_SHOW_LOG) {
            return;
        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
            case SYSO:
                printDefault(type, tag, headString + msg);
                break;
            case JSON:
                printJson(tag, headString, msg);
                break;
        }
    }


    public static void debugCall(int superLevel, Object... objects) {
        if (!IS_SHOW_LOG) {
            return;
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int index = 3 + superLevel;
        if (index >= stackTrace.length) {
            index = stackTrace.length - 1;
        } else if (index < 0) {
            index = 0;
        }
        StackTraceElement targetElement = stackTrace[index];
        String headString = getHeadString(targetElement);
        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        printDefault(D, TAG_DEFAULT + targetElement.getClassName(), headString + msg);
    }


    public static String getCallMethodByLevel(int superLevel) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int index = 3 + superLevel;
        if (index >= stackTrace.length) {
            index = stackTrace.length - 1;
        }
        StackTraceElement targetElement = stackTrace[index];
        String headString = getHeadString(targetElement);
        return headString;
    }

    private static void printDebug(Object... objects) {
        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        printDefault(D, tag, headString + msg);
    }


    private static String getHeadString(StackTraceElement targetElement) {
        String className = getClassName(targetElement.getClassName());

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }
        String headString = " [(" + className + ":" + lineNumber + ")#" + methodName + "()] ";
        // className + ":" + lineNumber + "." + methodName +"()";
        return headString;
    }

    private static String getClassName(String className) {
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("\\$")) {
            className = className.split("\\$")[0];
        }
        return className;
    }


    /***
     * 返回上层调用方法的层级
     * 例如：methodFirst() 调用了methodSecond() 调用了MethodThird()
     * 如果floor = 2, 则会打印出调用方法的层级
     * @param floor
     * @return
     */
    public static String getStackTraceFloorName(int floor) {
        if (floor < 1) {
            floor = 1;
        }

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append("【");
        for (int i = 0, stackTraceLength = stackTrace.length; i < floor && i <= stackTraceLength - 4; i++) {
            StackTraceElement targetElement = stackTrace[4 + i];
            sb.append(getHeadString(targetElement));
            if (i < floor - 1) {
                sb.append("-->");
            }
        }
        sb.append("】");

        return sb.toString();
    }


    private static String[] wrapperContent(int stackTraceIndex, Object... objects) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[stackTraceIndex];
        String tag = TAG_DEFAULT + getClassName(targetElement.getClassName());

        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        String headString = getHeadString(targetElement); //"[(" + className + ":" + lineNumber + ")#" + methodName +"()] ";
        return new String[]{tag, msg, headString};
    }

//    @NonNull
//    private static String getTag(String tagStr, String className) {
//        String tag = (tagStr == null ? className : tagStr);
//        //给tag加上default标记前缀，便于在logcat筛选查找
//        if (!tag.startsWith(TAG_DEFAULT)){
//            tag = TAG_DEFAULT +"_" + tag;
//        }
//        return tag;
//    }

    private static String getObjectsString(Object... objects) {
        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = objects[0];
            return object == null ? NULL : object.toString();
        }
    }


    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }


    public static void printJson(String tag, String headString, String msg) {
        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "║ " + line);
        }
        printLine(tag, false);
    }


    public static void printDefault(int type, String tag, String msg) {
        if (!IS_SHOW_LOG) {
            return;
        }
        int index = 0;
        int length = msg.length();
        int countOfSub = length / MAX_LENGTH;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + MAX_LENGTH);
                printSub(type, tag, sub);
                index += MAX_LENGTH;
            }
            printSub(type, tag, msg.substring(index, length));
        } else {
            printSub(type, tag, msg);
        }
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case V:
                Log.v(tag, sub);
                break;
            case D:
                Log.d(tag, sub);
                break;
            case I:
                Log.i(tag, sub);
                break;
            case W:
                Log.w(tag, sub);
                break;
            case E:
                Log.e(tag, sub);
                break;
            case A:
                Log.wtf(tag, sub);
                break;
            case SYSO:
                System.out.println(sub);
                break;
        }
    }

}
