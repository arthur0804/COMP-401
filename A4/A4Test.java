package a4test;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import a4.*;

public class A4Test {
	
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel randomColor = new ColorPixel(0.5, 0.42, 0.13);
	
	
	// Valid Pixel 2d Arrays
	Pixel[][] rgbPicture = { { red, red, red }, { green, green, green }, { blue, blue, blue } };
	Pixel[][] randomPicture = { { blue, randomColor, randomColor, red }, { green, red, green, randomColor } };
	// Invalid Pixel 2d Arrays
	Pixel[][] noWidthPicture = { {}, {} };
	Pixel[][] noHeightPicture = { {} };
	Pixel[][] differentHeightPicture = { { red, blue, green }, { red, green, blue }, { red, green } };
	Pixel[][] includesNullRowsPicture = { { red, red }, null, { blue, blue } };
	Pixel[][] includesNullPixelsPicture = { { randomColor, blue, red, blue }, { green, green, randomColor, blue },
				{ blue, blue, randomColor, null } };
	
	static public String[] getTestNames() {
		String[] test_names = new String[10];
		test_names[0] = "testCaption";
		test_names[1] = "testOutOfRangeException";
		test_names[2] = "testGetPixel";
		test_names[3] = "testPaintMethod";
		test_names[4] = "testConstructor";
		test_names[5] = "testGetters";
		test_names[6] = "testExtractMethod";
		test_names[7] = "testSampleMethod";
		test_names[8] = "testZigzagMethod";
		test_names[9] = "testTileMethod";
		return test_names;
	}
	
	// test 1
	@Test
	public void testCaption() {
		Pixel[][] pixel = new Pixel[3][3];
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				pixel[x][y] = new ColorPixel(1, 1, 1);
			}
		}
		Picture picture = new MutablePixelArrayPicture(pixel, "test caption");
		assertTrue(picture.getCaption()!=null);
		assertTrue(picture.getCaption().equals("test caption"));
		picture.setCaption("Hello World");
		assertFalse(picture.getCaption().equals("test caption"));
	}
	
	// test 2
	@Test
	public void testOutOfRangeException() {
		Pixel[][] pixel = new Pixel[3][3];
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				pixel[x][y] = red;
			}
		}
		Picture picture = new MutablePixelArrayPicture(pixel, "test2");
		try {
			picture.sample(-1, -1, -2, -2);
		}catch (IllegalArgumentException e) {
		}
		try {
			picture.getPixel(5, 5);
		}catch (IllegalArgumentException e) {
		}
		try {
			picture.window(6, 6);
		}catch (IllegalArgumentException e) {
		}
		try {
			picture.tile(10, 10);
		}catch (IllegalArgumentException e) {
		}
	}
	
	// test 3
	@Test
	public void testGetPixel() {
		Pixel[][] pixel = new Pixel[3][3];
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				pixel[x][y] = red;
			}
		}
		Picture picture = new MutablePixelArrayPicture(pixel, "test3");
		assertTrue(checkPixelEquality(picture.getPixel(1, 1),red));
	}
	
	// test 4
	@Test
	public void testPaintMethod() {
		Pixel[][] pixel = new Pixel[3][3];
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				pixel[x][y] = red;
			}
		}
		Picture picture = new MutablePixelArrayPicture(pixel, "test4");
		Picture result = picture.paint(1, 1, blue);
		assertEquals(picture, result);
		Pixel pixel11 = picture.getPixel(1, 1);
		checkPixelEquality(blue, pixel11);
	}
	
	// test 5
	@Test
	public void testConstructor() {
		try {
			Picture picture = new MutablePixelArrayPicture(noWidthPicture, "test5");
		}catch (IllegalArgumentException e) {
		}
		try {
			Picture picture = new MutablePixelArrayPicture(noHeightPicture, "test5");
		}catch (IllegalArgumentException e) {
		}
		try {
			Picture picture = new ImmutablePixelArrayPicture(differentHeightPicture, "test5");
		}catch (IllegalArgumentException e) {
		}
		try {
			Picture picture = new ImmutablePixelArrayPicture(includesNullRowsPicture, "test5");
		}catch (IllegalArgumentException e) {
		}
		try {
			Picture picture = new ImmutablePixelArrayPicture(includesNullPixelsPicture, "test5");
		}catch (IllegalArgumentException e) {
		}
	}
	
	// test 6
	@Test
	public void testGetters() {
		Picture picture = new MutablePixelArrayPicture(rgbPicture, "test6");
		Picture picture2 = new ImmutablePixelArrayPicture(rgbPicture, "test6");
		assertEquals(3, picture.getWidth());
		assertEquals(3, picture2.getHeight());
	}
	
	// test 7
	@Test
	public void testExtractMethod() {
		Pixel[][] pixel = new Pixel[3][3];
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				pixel[x][y] = red;
			}
		}
		Picture picture = new MutablePixelArrayPicture(pixel, "test7");
		SubPicture subPicture = picture.extract(0, 0, 2, 2);
		assertEquals(subPicture.getSource(), picture);
		assertEquals(subPicture.getHeight(),2);
		assertEquals(subPicture.getWidth(),2);
		for(int x = 0; x < 2; x++) {
			for(int y = 0; y < 2; y++) {
				assertTrue(checkPixelEquality(subPicture.getPixel(x, y), red));
			}
		}
	}
	
	// test 8
	@Test
	public void testSampleMethod() {
		Pixel[][] pixel = new Pixel[10][10];
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				pixel[x][y] = red;
			}
		}
		Picture picture = new MutablePixelArrayPicture(pixel, "test8");
		Iterator<Pixel> sample_iter = picture.sample(0,0,2,2);
		while(sample_iter.hasNext()) {
			Pixel p = sample_iter.next();
			assertTrue(checkPixelEquality(p, red));
		}
	}
	
	// test 9 
	@Test
	public void testZigzagMethod() {
		Pixel[][] pixel = new Pixel[3][3];
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				pixel[x][y] = red;
			}
		}
		Picture picture = new MutablePixelArrayPicture(pixel, "test9");
		Iterator<Pixel> sample_iter = picture.zigzag();
		while(sample_iter.hasNext()) {
			Pixel p = sample_iter.next();
			assertTrue(checkPixelEquality(p, red));
		}
	}
	
	// test 10
	@Test
	public void testTileMethod() {
		Pixel[][] pixel = new Pixel[10][10];
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				pixel[x][y] = red;
			}
		}
		Picture picture = new MutablePixelArrayPicture(pixel, "test10");
		Iterator<SubPicture> sample_iter = picture.tile(2, 2);
		while(sample_iter.hasNext()) {
			SubPicture subpicture = sample_iter.next();
			assertEquals(subpicture.getHeight(), 2);
			assertEquals(subpicture.getWidth(), 2);
			for(int i = 0; i < subpicture.getWidth(); i ++) {
				for(int j = 0 ; j < subpicture.getHeight(); j++) {
					assertTrue(checkPixelEquality(subpicture.getPixel(i,j), red));
				}
			}
		}	
	}
	
	private static boolean checkPixelEquality(Pixel a, Pixel b) {
		assertEquals(a.getRed(), b.getRed(), 0.001);
		assertEquals(a.getGreen(), b.getGreen(), 0.001);
		assertEquals(a.getBlue(), b.getBlue(), 0.001);
		return true;
	}
	
	
}
