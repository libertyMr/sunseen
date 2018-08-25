package com.sunseen.spcontrolsystem.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.model.RunModel;
import com.sunseen.spcontrolsystem.model.SystemRunModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;
import com.sunseen.spcontrolsystem.view.SPStatusView;
import com.sunseen.spcontrolsystem.view.SwitchView;

public class HomePageFragment extends Fragment implements View.OnClickListener, SwitchView.SwitchViewOnClickListener {

    private View ambient_temperature;
    private View ambient_humidity;
    private View swimming_pool_t;
    private View ph_value;
    private View orp_value;
    private View chlorine;
    private View limpid_grade;

    private TextView ambient_temperature_title;
    private TextView ambient_humidity_title;
    private TextView swimming_pool_t_title;
    private TextView ph_value_title;
    private TextView orp_value_title;
    private TextView chlorine_title;
    private TextView limpid_grade_title;
    private TextView ambient_temperature_value;
    private TextView ambient_humidity_value;
    private TextView swimming_pool_t_value;
    private TextView ph_value_value;
    private TextView orp_value_value;
    private TextView chlorine_value;
    private TextView limpid_grade_value;

    private TextView tv_current_mode;//

    private TextView normal_operation;
    private LinearLayout normal_operation_item;
    private TextView tv_filter_mode;
    private TextView tv_not_filter;
    private TextView tv_self_drainage;
    private TextView tv_force_drainage;
    private TextView tv_backwash_mode;
    private TextView tv_close_mode;

    private TextView water_temperature_mode;
    private LinearLayout water_temperature_mode_item;
    private TextView tv_water_heating_mode;
    private TextView tv_room_temperature;

    private TextView dehumidify_mode;
    private LinearLayout dehumidify_mode_item;
    private TextView tv_dehumidify_heat;
    private TextView tv_dehumidify_heat_water;
    private TextView tv_dehumidify_refrigeration;

    private LinearLayout computer_room_running;
    private SwitchView underwater_total_lamp_switch;

    private SPStatusView sp_status_view;

    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.homepage_fragment, container, false);
            LogMsg.printDebugMsg("HomePageFragment onCreateView");
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    private void initView(View v) {
        ambient_temperature = v.findViewById(R.id.ambient_temperature);
        ambient_humidity = v.findViewById(R.id.ambient_humidity);
        swimming_pool_t = v.findViewById(R.id.swimming_pool_t);
        ph_value = v.findViewById(R.id.ph_value);
        orp_value = v.findViewById(R.id.orp_value);
        chlorine = v.findViewById(R.id.chlorine);
        limpid_grade = v.findViewById(R.id.limpid_grade);

        tv_current_mode = v.findViewById(R.id.tv_current_mode);//

        normal_operation = v.findViewById(R.id.normal_operation);
        normal_operation_item = v.findViewById(R.id.normal_operation_item);
        tv_filter_mode = v.findViewById(R.id.tv_filter_mode);
        tv_not_filter = v.findViewById(R.id.tv_not_filter);
        tv_self_drainage = v.findViewById(R.id.tv_self_drainage);
        tv_force_drainage = v.findViewById(R.id.tv_force_drainage);
        tv_backwash_mode = v.findViewById(R.id.tv_backwash_mode);
        tv_close_mode = v.findViewById(R.id.tv_close_mode);

        water_temperature_mode = v.findViewById(R.id.water_temperature_mode);
        water_temperature_mode_item = v.findViewById(R.id.water_temperature_mode_item);
        tv_water_heating_mode = v.findViewById(R.id.tv_water_heating_mode);
        tv_room_temperature = v.findViewById(R.id.tv_room_temperature);

        dehumidify_mode = v.findViewById(R.id.dehumidify_mode);
        dehumidify_mode_item = v.findViewById(R.id.dehumidify_mode_item);
        tv_dehumidify_heat = v.findViewById(R.id.tv_dehumidify_heat);
        tv_dehumidify_heat_water = v.findViewById(R.id.tv_dehumidify_heat_water);
        tv_dehumidify_refrigeration = v.findViewById(R.id.tv_dehumidify_refrigeration);

        computer_room_running = v.findViewById(R.id.computer_room_running);
        underwater_total_lamp_switch = v.findViewById(R.id.underwater_total_lamp_switch);

        sp_status_view = v.findViewById(R.id.sp_status_view);

        ambient_temperature_title = ambient_temperature.findViewById(R.id.title);
        ambient_humidity_title = ambient_humidity.findViewById(R.id.title);
        swimming_pool_t_title = swimming_pool_t.findViewById(R.id.title);
        ph_value_title = ph_value.findViewById(R.id.title);
        orp_value_title = orp_value.findViewById(R.id.title);
        chlorine_title = chlorine.findViewById(R.id.title);
        limpid_grade_title = limpid_grade.findViewById(R.id.title);

        ambient_temperature_title.setText(R.string.ambient_temperature);
        ambient_humidity_title.setText(R.string.ambient_humidity);
        swimming_pool_t_title.setText(R.string.swimming_pool_t);
        ph_value_title.setText(R.string.ph_value);
        orp_value_title.setText(R.string.orp_value);
        chlorine_title.setText(R.string.chlorine);
        limpid_grade_title.setText(R.string.limpid_grade);

        ambient_temperature_value = ambient_temperature.findViewById(R.id.value);
        ambient_humidity_value = ambient_humidity.findViewById(R.id.value);
        swimming_pool_t_value = swimming_pool_t.findViewById(R.id.value);
        ph_value_value = ph_value.findViewById(R.id.value);
        orp_value_value = orp_value.findViewById(R.id.value);
        chlorine_value = chlorine.findViewById(R.id.value);
        limpid_grade_value = limpid_grade.findViewById(R.id.value);

        ambient_temperature_value.setText("25°C");
        ambient_humidity_value.setText("45％");
        swimming_pool_t_value.setText("23°C");
        ph_value_value.setText("8");
        orp_value_value.setText("8mV");
        chlorine_value.setText("8mg/L");
        limpid_grade_value.setText("7NTU");

        normal_operation.setOnClickListener(this);
        water_temperature_mode.setOnClickListener(this);
        dehumidify_mode.setOnClickListener(this);

        tv_filter_mode.setOnClickListener(this);
        tv_not_filter.setOnClickListener(this);
        tv_self_drainage.setOnClickListener(this);
        tv_force_drainage.setOnClickListener(this);
        tv_backwash_mode.setOnClickListener(this);
        tv_close_mode.setOnClickListener(this);

        tv_water_heating_mode.setOnClickListener(this);
        tv_room_temperature.setOnClickListener(this);

        tv_dehumidify_heat.setOnClickListener(this);
        tv_dehumidify_heat_water.setOnClickListener(this);
        tv_dehumidify_refrigeration.setOnClickListener(this);

        underwater_total_lamp_switch.setListener(this);
        underwater_total_lamp_switch.setmColorOnView(getResources().getColor(R.color.yellow_self));
        underwater_total_lamp_switch.setmColorOffView(Color.GRAY);
        underwater_total_lamp_switch.setmTextSize(10);
        underwater_total_lamp_switch.setmColorText(getResources().getColor(R.color.yellow_self));

        loseFocus(water_temperature_mode);
        loseFocus(normal_operation);
        loseFocus(dehumidify_mode);
    }

    @Override
    public void onClick(View v) {
        LogMsg.printDebugMsg("onClick");
        switch (v.getId()) {
            case R.id.normal_operation://正常运行
            {
                LogMsg.printDebugMsg("normal_operation");
                if (!normal_operation.hasFocus()) {
                    getFocus(normal_operation);
                    loseFocus(water_temperature_mode);
                    loseFocus(dehumidify_mode);
                    normal_operation.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_focus), null);
                    water_temperature_mode.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_no_focus), null);
                    dehumidify_mode.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_no_focus), null);
                    normal_operation_item.setVisibility(View.VISIBLE);
                    water_temperature_mode_item.setVisibility(View.GONE);
                    dehumidify_mode_item.setVisibility(View.GONE);
                } else {
                    loseFocus(normal_operation);
                    normal_operation_item.setVisibility(View.GONE);
                    normal_operation.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_no_focus), null);
                }
                break;
            }
            case R.id.water_temperature_mode://水温模式
            {
                LogMsg.printDebugMsg("water_temperature_mode");
                if (!water_temperature_mode.hasFocus()) {
                    getFocus(water_temperature_mode);
                    loseFocus(normal_operation);
                    loseFocus(dehumidify_mode);
                    normal_operation.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_no_focus), null);
                    water_temperature_mode.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_focus), null);
                    dehumidify_mode.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_no_focus), null);
                    normal_operation_item.setVisibility(View.GONE);
                    water_temperature_mode_item.setVisibility(View.VISIBLE);
                    dehumidify_mode_item.setVisibility(View.GONE);
                } else {
                    loseFocus(water_temperature_mode);
                    water_temperature_mode_item.setVisibility(View.GONE);
                    water_temperature_mode.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_no_focus), null);
                }
                break;
            }
            case R.id.dehumidify_mode://除湿模式
            {
                LogMsg.printDebugMsg("dehumidify_mode");
                if (!dehumidify_mode.hasFocus()) {
                    getFocus(dehumidify_mode);
                    loseFocus(water_temperature_mode);
                    loseFocus(normal_operation);
                    normal_operation.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_no_focus), null);
                    water_temperature_mode.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_no_focus), null);
                    dehumidify_mode.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_focus), null);
                    normal_operation_item.setVisibility(View.GONE);
                    water_temperature_mode_item.setVisibility(View.GONE);
                    dehumidify_mode_item.setVisibility(View.VISIBLE);
                } else {
                    loseFocus(dehumidify_mode);
                    dehumidify_mode_item.setVisibility(View.GONE);
                    dehumidify_mode.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, getResources().getDrawable(R.drawable.tv_right_no_focus), null);
                }
                break;
            }
            case R.id.tv_filter_mode:
                RunModeChange(RunModel.FILTER_MODLE);
                break;
            case R.id.tv_not_filter:
                RunModeChange(RunModel.NOT_FILTER_MODLE);
                break;
            case R.id.tv_self_drainage:
                RunModeChange(RunModel.SELF_DRAINAGE_MODLE);
                break;
            case R.id.tv_force_drainage:
                RunModeChange(RunModel.FORCE_DRAINAGE_MODLE);
                break;
            case R.id.tv_backwash_mode:
                RunModeChange(RunModel.BACKWASH);
                break;
            case R.id.tv_close_mode:
                RunModeChange(RunModel.CLOSEMODE);
                break;

            case R.id.tv_water_heating_mode:
                RunModeChange(RunModel.HEAT_MODLE);
                break;
            case R.id.tv_room_temperature:
                RunModeChange(RunModel.NORMA_MODLE);
                break;

            case R.id.tv_dehumidify_heat:
                RunModeChange(RunModel.DEHUMIDIFIER_MODLE);
                break;
            case R.id.tv_dehumidify_heat_water:
                RunModeChange(RunModel.DEHUMIDIFIER_WATER_MODLE);
                break;
            case R.id.tv_dehumidify_refrigeration:
                RunModeChange(RunModel.DEHUMIDIFIER_COLD_MODLE);
                break;

            case R.id.computer_room_running:
                //TODO 拉起机房运行
                break;
        }
    }

    @Override
    public void isClicked(View view, boolean isClicked) {
        //调用activity方法
        if (isClicked) {
            sp_status_view.setLight(true, 0);
        } else {
            sp_status_view.setLight(false, 0);
        }
    }

    public void setWaterHeigth(int heigth)
    {
        sp_status_view.setWater(8,true);
    }

    private void RunModeChange(int mode){
        SystemRunModel.SYSTEM_RUNNING_MODLE = mode;
        //TODO 调用activity中的发送对象发送数据
    }

    /**
     * @param view view 获取焦点
     */
    private void getFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocusFromTouch();
    }

    private void loseFocus(View view) {
        view.setFocusableInTouchMode(false);
        view.setFocusable(false);
    }

    /**
     * @param value 设置环境温度
     */
    public void setAmbient_temperature_value(String value) {
        ambient_temperature_value.setText(value + "°C");
    }

    /**
     * @param value 设置环境湿度
     */
    public void setAmbient_humidity_value(String value) {
        ambient_humidity_value.setText(value + "％");
    }

    /**
     * @param value 设置泳池水温
     */
    public void setSwimming_pool_t_value(String value) {

        swimming_pool_t_value.setText(value + "°C");
    }

    /**
     * @param value ph
     */
    public void setPh_value_value(String value) {
        ph_value_value.setText(value);
    }

    /**
     * @param value orp
     */
    public void setOrp_value_value(String value) {
        orp_value_value.setText(value + "mV");
    }

    /**
     * @param value 余氯
     */
    public void setChlorine_value(String value) {
        chlorine_value.setText(value + "mg/L");
    }

    /**
     * @param value 浊度
     */
    public void setLimpid_grade_value(String value) {
        limpid_grade_value.setText(value + "NTU");
    }
}
