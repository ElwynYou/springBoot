package com.elwyn.modules.baseinfo.entity;

import com.rainsoft.common.ShapeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name com.rainsoft.modules.baseinfo.entity.MapShape
 * @Description
 * 
 * @Author Sugar
 * @Version 2017年4月8日 下午5:38:17
 * @Copyright 上海云辰信息科技有限公司
 */
public class MapShape implements Serializable {
	private ShapeType shapeType;
	private double radius;
	private List<LatLng> latlng;
	
	private MapShape() {};
	
	public static MapShape newCircle(double radius, double longitude, double latitude) {
		MapShape shape = new MapShape();
		shape.shapeType = ShapeType.CIRCLE;
		shape.radius = radius;
		shape.latlng = new ArrayList<LatLng>();
		shape.latlng.add(new LatLng(longitude, latitude));
		return shape;
	}

	public static MapShape newRectangle(LatLng leftTopLatlng, LatLng rightBottomLatlng) {
		MapShape shape = new MapShape();
		shape.shapeType = ShapeType.RECTANGLE;
		shape.latlng = new ArrayList<LatLng>();
		shape.latlng.add(leftTopLatlng);
		shape.latlng.add(rightBottomLatlng);
		return shape;
	}
	
	public static MapShape newPolygon(List<LatLng> latlng) {
		MapShape shape = new MapShape();
		shape.shapeType = ShapeType.POLYGON;
		shape.latlng = latlng;
		return shape;
	}
	
	/**
	 * @return the shapeType
	 */
	public ShapeType getShapeType() {
		return shapeType;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @return the latlng
	 */
	public List<LatLng> getLatlng() {
		return latlng;
	}
	
	public int getType() {
		return shapeType.getValue();
	}

	public static class LatLng implements Serializable {
		private double longitude;
		private double latitude;

		public LatLng(double longitude, double latitude) {
			this.longitude = longitude;
			this.latitude = latitude;
		}

		/**
		 * @return the longitude
		 */
		public double getLongitude() {
			return longitude;
		}

		/**
		 * @param longitude
		 *            the longitude to set
		 */
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		/**
		 * @return the latitude
		 */
		public double getLatitude() {
			return latitude;
		}

		/**
		 * @param latitude
		 *            the latitude to set
		 */
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
	}
}
