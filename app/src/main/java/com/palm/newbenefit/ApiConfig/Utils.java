package com.palm.newbenefit.ApiConfig;

import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.DatePicker;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static void desablePreDatePicker(Context mContext, final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    public static void normal(Context mContext, final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    public static void desablePostDatePickerNormal(Context mContext, final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        },


                myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));



        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() );
        datePickerDialog.show();
    }


    public static void desablePostDatePicker(Context mContext, final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        },


                myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));



        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }


    public static void desablePostDatePickers(Context mContext, final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        },


                myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));



        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() );
        datePickerDialog.show();
    }

    public static void adult_date(Context mContext, final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }


    public static void maxdate(Context mContext, int date,int month,int year,final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        month += 1;
        String month1 = ("0"+month);
        String day1 = ("0"+date);
        String selectedDate = year + "-" + month1.substring(month1.length() - 2) + "-"+ day1.substring(day1.length() - 2);

        try {
            c.setTime(sdf.parse(selectedDate));
            c.add(Calendar.YEAR, 0);
//            c.add(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
    //   datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        datePickerDialog.getDatePicker().setMinDate(date+month+year-1000);
        datePickerDialog.show();
    }


    public static void maxdateAdult(Context mContext, int date,int month,int year,final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

       // datePickerDialog.getDatePicker().setMaxDate(c.currentTimeMillis());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        month += 1;
        String month1 = ("0"+month);
        String day1 = ("0"+date);
        String selectedDate = year + "-" + month1.substring(month1.length() - 2) + "-"+ day1.substring(day1.length() - 2);

        try {
            c.setTime(sdf.parse(selectedDate));
//            c.add(Calendar.YEAR, 1);
//            c.add(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }



       // datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        c = Calendar.getInstance();

//        Calendar date2 = Calendar.getInstance();
//        long diff = c.getTimeInMillis() - date2.getTimeInMillis();
//        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
//        c.add(Calendar.DAY_OF_MONTH, (int) dayCount);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());




       // setDatePickerDialogRange(dialog);
//        datePickerDialog.getDatePicker().setMinDate(date+month+year-1000);
        datePickerDialog.show();
    }


    public static void maxdateAdultNormal(Context mContext, int date,int month,int year,final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        // datePickerDialog.getDatePicker().setMaxDate(c.currentTimeMillis());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        month += 1;
        String month1 = ("0"+month);
        String day1 = ("0"+date);
        String selectedDate = year + "-" + month1.substring(month1.length() - 2) + "-"+ day1.substring(day1.length() - 2);

        try {
            c.setTime(sdf.parse(selectedDate));
//            c.add(Calendar.YEAR, 1);
//            c.add(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        // datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        c = Calendar.getInstance();

//        Calendar date2 = Calendar.getInstance();
//        long diff = c.getTimeInMillis() - date2.getTimeInMillis();
//        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
//        c.add(Calendar.DAY_OF_MONTH, (int) dayCount);
      //  datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());




        // setDatePickerDialogRange(dialog);
//        datePickerDialog.getDatePicker().setMinDate(date+month+year-1000);
        datePickerDialog.show();
    }


    public static void maxdateAdultBill(Context mContext, int date,int date_a,int month,int month_a,int year,int year_a,final MyDateListener listener) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                listener.onDateSelect(selectedYear, selectedMonth, selectedDay);
                /*myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                myCalendar.set(Calendar.MONTH, selectedMonth);
                myCalendar.set(Calendar.YEAR, selectedYear);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                if (id == R.id.create_task_deadline_datetime) {
                    create_task_deadline_datetime.setText(sdf.format(myCalendar.getTime()));

                }*/
            }

        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        // datePickerDialog.getDatePicker().setMaxDate(c.currentTimeMillis());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

       // month = 1;
        String month1 = ("0"+month);
        String day1 = ("0"+date);
        String selectedDate = year + "-" + month1.substring(month1.length() - 2) + "-"+ day1.substring(day1.length() - 2);

        try {
            c.setTime(sdf.parse(selectedDate));
//            c.add(Calendar.YEAR, 1);
//            c.add(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        // datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        c = Calendar.getInstance();

       // month_a = 1;
        String month1_a = ("0"+month_a);
        String day1_a = ("0"+date_a);
        String selectedDate_a = year_a + "-" + month1_a.substring(month1_a.length() - 2) + "-"+ day1_a.substring(day1_a.length() - 2);

        try {
            c.setTime(sdf.parse(selectedDate_a));
//            c.add(Calendar.YEAR, 1);
//            c.add(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());




        // setDatePickerDialogRange(dialog);
//        datePickerDialog.getDatePicker().setMinDate(date+month+year-1000);
        datePickerDialog.show();
    }



    public static String getLoactionNameFromLatLong(Context context, double lat, double lang) throws Exception {
        String location = null;
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lang, 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);

           // Config.d("cityName:" + cityName + " statename:" + stateName + "countryName:" + countryName);
            location = stateName + "," + cityName + "," + countryName;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return location;
    }
}