package com.bc.pmpheep.back.util;

import org.apache.poi.ss.usermodel.Cell;

import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：Object工具类
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-19
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public final class ObjectUtil {
    private ObjectUtil() {
    }

    /**
     * 检测对象是否不为空
     * 
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 检测对象是否为空
     * 
     * @param s
     * @return 为空则返回true，否则返回false
     */
    public static boolean isNull(Object obj) {
        return null == obj;
    }
    
    /**
     * 
     * Description:解析Excel文件时动态判断单元格类型并返回数值
     * @author:Administrator
     * @date:2017年11月30日上午11:26:45
     * @param 
     * @return Integer
     */
    @SuppressWarnings("deprecation")
	public static Integer getCellValue(Cell cell){
    	if (null == cell){
    		return 0 ;
    	}
    	int type = cell.getCellType();
 		Integer value = 0;
 		switch (type) {
		case 0://数字 Cell.CELL_TYPE_NUMERIC	
			Double cellValue = cell.getNumericCellValue();
			value = Integer.valueOf(cellValue.intValue());
			break;
		case 1://字符串Cell.CELL_TYPE_STRING
			value = Integer.valueOf(cell.getStringCellValue().trim());
			break;
		case 3://空值 Cell.CELL_TYPE_BLANK
			value = 0;
			break;
		case 5://错误 Cell.CELL_TYPE_ERROR
			value = 0;
			break;
		default:
			value = 0;
			break;
		}
 		return value;
    }
    
    /**
     * 
     * Description:解析Excel文件时动态判断单元格类型并返回小数
     * @author:lyc
     * @date:2017年11月30日上午11:34:03
     * @param 
     * @return Double
     */
    @SuppressWarnings("deprecation")
	public static Double getValue(Cell cell){
    	if (null == cell){
    		return 0.0 ;
    	}
    	int type = cell.getCellType();
 		Double value = 0.0;
 		switch (type) {
		case 0://数字 Cell.CELL_TYPE_NUMERIC	
			value = cell.getNumericCellValue();
			break;
		case 1://字符串Cell.CELL_TYPE_STRING
			try{
			value = Double.valueOf(cell.getStringCellValue().trim());
			} catch(NumberFormatException e){
				throw new CheckedServiceException(CheckedExceptionBusiness.EXCEL,
						CheckedExceptionResult.ILLEGAL_PARAM, "单元格内容需为数字");
			}
			break;
		case 3://空值 Cell.CELL_TYPE_BLANK
			value = 0.0;
			break;
		case 5://错误 Cell.CELL_TYPE_ERROR
			value = 0.0;
			break;
		default:
			value = 0.0;
			break;
		}
 		return value;
    }


	public static boolean isNumber(String str) {
		boolean re = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				re = false;
				break;
			}
		}
		return re;

	}
	}
