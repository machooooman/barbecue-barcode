package com.sample.barcode.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.sample.barcode.entity.BarcodeType;
import com.sample.barcode.entity.BarcodeVO;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

/**
 * 2019.07.15
 * @author 박선주
 *
 */
@Service
public class BarcodeUtil {
	
	/**
	 * get barcode image
	 * @param code
	 * @param type
	 * @return
	 * @throws BarcodeException
	 * @throws OutputException
	 * @throws IOException
	 */
	public Image toImage(String code, BarcodeType type) throws BarcodeException, OutputException, IOException {
		return this.toImage(new BarcodeVO(code, type));
	}
	
	/**
	 * get barcode image
	 * @param barcodeVO
	 * @return
	 * @throws BarcodeException
	 * @throws OutputException
	 * @throws IOException
	 */
	
	public Image toImage(BarcodeVO barcodeVO) throws BarcodeException, OutputException, IOException {
		Barcode barcode = this.build(barcodeVO);
		Image image = this.createImage(this.createFile(barcode.getData()), barcode);
		return image;
	}
	
	/**
	 * get barcode file
	 * @param code
	 * @param type
	 * @return
	 * @throws BarcodeException
	 * @throws OutputException
	 * @throws IOException
	 */
	public File toFile(String code, BarcodeType type) throws BarcodeException, OutputException, IOException {
		return this.toFile(new BarcodeVO(code, type));
	}
	
	/**
	 * get barcode file
	 * @param barcodeVO
	 * @return
	 * @throws BarcodeException
	 * @throws OutputException
	 * @throws IOException
	 */
	public File toFile(BarcodeVO barcodeVO) throws BarcodeException, OutputException, IOException {
		Barcode barcode = this.build(barcodeVO);
		File file = this.createFile(barcode.getData());
		BufferedImage image = this.createImage(file, barcode);
		ImageIO.write(image, "png", file);
		return file;
	}
	
	/**
	 * get barcode bytes
	 * @param code
	 * @param type
	 * @return
	 * @throws BarcodeException
	 * @throws OutputException
	 * @throws IOException
	 */
	public byte[] toByte(String code, BarcodeType type) throws BarcodeException, OutputException, IOException {
		return this.toByte(new BarcodeVO(code, type));
	}
	
	/**
	 * get barcode bytes
	 * @param barcodeVO
	 * @return
	 * @throws BarcodeException
	 * @throws OutputException
	 * @throws IOException
	 */
	public byte[] toByte(BarcodeVO barcodeVO) throws BarcodeException, OutputException, IOException {
		Barcode barcode = this.build(barcodeVO);
		this.createImage(this.createFile(barcode.getData()), barcode);
		
		//BarcodeImageHandler 사용
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BarcodeImageHandler.writePNG(barcode, baos);
		
		//original 
//		BufferedImage image = this.createImage(this.createFile(barcode.getData()), barcode);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ImageIO.write(image, "png", baos);
		
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
	
	/**
	 * create barcode
	 * @param barcodeVO
	 * @return
	 * @throws BarcodeException
	 */
	private Barcode build (BarcodeVO barcodeVO) throws BarcodeException {
		String code = barcodeVO.getCode();
		Barcode barcode = barcodeVO.getType().equals(BarcodeType.CODE39) ? 
				BarcodeFactory.createCode39(code, true) : BarcodeFactory.createCode128(code);
		this.setOption(barcode, barcodeVO);
		return barcode;
	}
	
	/**
	 * set options
	 * @param barcode
	 * @param barcodeVO
	 */
	private void setOption(Barcode barcode, BarcodeVO barcodeVO) {
		barcode.setLabel(barcodeVO.getCode());
		barcode.setSize(barcodeVO.getHeight(), barcodeVO.getWidth());
		barcode.setFont(barcodeVO.getFont());
	}
	
	/**
	 * create BufferedImage
	 * @param file
	 * @param barcode
	 * @return
	 * @throws OutputException
	 */
	private BufferedImage createImage(File file, Barcode barcode) throws OutputException {
		BarcodeImageHandler.savePNG(barcode, file);
		BufferedImage bi = BarcodeImageHandler.getImage(barcode);
		return bi;
	}

	/**
	 * create File
	 * @param name
	 * @return
	 * @throws OutputException
	 * @throws IOException
	 */
	private File createFile(String name) throws OutputException, IOException {
		File file = new File(name + ".png");
		return file;
	}
}
