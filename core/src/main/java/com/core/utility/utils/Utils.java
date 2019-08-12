package com.core.utility.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.core.dc.utility.R;

/**
 * Created by ChungLD on 20/09/2016.
 */
@SuppressWarnings("ALL")
public class Utils {
    public static final DecimalFormat FORMAT_CURRENCY = new DecimalFormat("###,###,###,###");
    public static String TIME_FOMART = "hh:mm (a)";
    public static String DATE_FOMART = "yyyy-mm'T'hh:mm:ss";
    public static String DATE_TIME_FOMART = "yyyy-MM-dd HH:mm:ss";

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getDate(long time, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        String date = DateFormat.format(format, cal).toString();
        return date;
    }

    public static Date getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.getTime();
    }

    public static String getDateValidate(long time, String toDay) {
        String today = getDate(System.currentTimeMillis(), "dd/MM/yyyy");
        String request = getDate(time, "dd/MM/yyyy");
        return today.equals(request) ? getDate(time, "HH:mm") + " " + toDay : request;
    }

    public static int getMinutes(long time) {
        return (int) (time / 60);
    }

    @SuppressLint("DefaultLocale")
    public static String getTimeFromMillis(long time) {
        int h, m, s;
        h = m = s = 0;
        s = (int) (time / 1000);
        if (s >= 60) {
            m = s / 60;
            s = s % 60;
        }

        if (m >= 60) {
            h = m / 60;
            m = m % 60;
        }
        if (h != 0)
            return String.format("%02d:%02d:%02d", h, m, s);

        return String.format("%02d:%02d", m, s);
    }

    public static int randInt(int min, int max) {
        Random r = new Random();
        int randomNum = r.nextInt(max - min) + min;
        return randomNum;
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static Date getDate(String date, String format) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String getDate(String date, String formatInput, String formatOutput) {
        Date dateData = getDate(date, formatInput);
        return getDate(dateData == null ? 0 : dateData.getTime(), formatOutput);
    }

    public static int getDrawableFromString(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static <T extends AppCompatActivity> Bitmap takeScreenshot(T activity) {
        try {
            View v1 = activity.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            bitmap = ImageProcess.ResizeBitmapW(bitmap, 200);
            return bitmap;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMD5Code(String data) {
        return getMD5EncryptedString(data == null ? Constants.EMPTY_STRING : data.trim());
    }

    private static String getMD5FromString(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(s.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getMD5EncryptedString(String encTarget) {
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        } // Encryption algorithm
        mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        while (md5.length() < 32) {
            md5 = "0" + md5;
        }
        return md5;
    }

    public static int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return Constants.ZERO_VALUE;
        }
    }

    public static boolean isEditTextEmpty(EditText editText) {
        return editText == null || (editText.getText().toString().trim().equals(Constants.EMPTY_STRING));
    }

    @SuppressLint("MissingPermission")
    public static boolean checkConnect(Context context) {
        ConnectivityManager connM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connM.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                || connM.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
                || connM.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED
                || connM.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
            return true;
        } else if (connM.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
                || connM.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    public static int convertGender(String s) {
        int g = 0;
        switch (s) {
            case "Nam":
                g = 1;
                break;
            case "Nữ":
                g = 2;
                break;
        }
        return g;
    }

    public static int convertPixelToDp(int pixel) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, displaymetrics);
    }

    public static long getTimeStampFromString(String value) {
        long result = 0;
        value = value.trim();
        value = value.replace(",", ".");
        String[] values = value.split("\\:");
        result += convertStringToLong(values[0]) * 60 * 60 * 1000;
        result += convertStringToLong(values[1]) * 60 * 1000;
        result += convertStringToLong(values[2].replace(".", ""));
        return result;
    }

    public static long convertStringToLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getTimeAgo(Context context, long pastTime) {
        long timeAgo = Calendar.getInstance().getTimeInMillis() - pastTime;

        int seconds = (int) timeAgo / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;
        int days = hours / 24;

        if (days > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(pastTime);
            if (days > 1) {
                if (calendar.get(Calendar.YEAR) > Calendar.getInstance().get(Calendar.YEAR)) {
                    return String.format("%02d/%02d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1);
                } else {
                    return String.format("%02d/%02d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1);
                }
            } else {
                return context.getString(R.string.yesterday_at);
            }

        }
        if (hours > 0) {
            return hours + (hours > 1 ? " " + context.getString(R.string.hours) : " " + context.getString(R.string.hour)) + " " + context.getString(R.string.ago);
        }
        if (minutes > 0) {
            return minutes + (minutes > 1 ? " " + context.getString(R.string.minutes) : " " + context.getString(R.string.minute)) + " " + context.getString(R.string.ago);
        }
        return context.getString(R.string.just_now);
    }

    public static String formatCurrency(String value) {
        return FORMAT_CURRENCY.format(convertStringToNumber(value));
    }

    public static String formatCurrency(long value) {
        return FORMAT_CURRENCY.format((value));
    }

    public static long convertStringToNumber(String value) {
        try {
            return (long) Double.parseDouble(value);
        } catch (Exception ignored) {
        }
        return 0;
    }

    public static double convertStringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static double convertStringToDouble(String value, double def) {
        try {
            return Double.parseDouble(value);
        } catch (Exception ex) {
            return def;
        }
    }

    public static String formatDate(String date) {
        String result = date;
        try {
            result = result.split(" ")[0];
            String[] dateArray = result.split("-");
            result = dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0];
        } catch (Exception ignored) {

        }

        return result;
    }

    public static String formatDateTime(String date) {
        String result = date;
        try {
            String[] dateTime = result.split(" ");
            result = dateTime[0];
            String[] dateArray = result.split("-");
            result = dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0];
            if (dateTime.length > 1) {
                result += Constants.SPACE_CHARACTER + dateTime[1];
            }
        } catch (Exception ignored) {

        }
        return result;
    }

    public static String decodeBase64(String base64) throws UnsupportedEncodingException {
        if (base64 == null) return "";
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        return new String(data, "UTF-8");
    }

    public static String encodeBase64(String text) throws UnsupportedEncodingException {
        if (text == null) return "";
        byte[] data = text.getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
    }
}
