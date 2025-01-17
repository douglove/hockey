/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sanselan.formats.tiff.photometricinterpreters;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.sanselan.ImageReadException;

public class PhotometricInterpreterPalette extends PhotometricInterpreter
{
	private final int[] fColorMap;

	public PhotometricInterpreterPalette(int fSamplesPerPixel,
			int fBitsPerSample[], int Predictor, int width, int height,
			int[] fColorMap)
	{
		super(fSamplesPerPixel, fBitsPerSample, Predictor, width, height);

		this.fColorMap = fColorMap;
	}

	public void interpretPixel(BufferedImage bi, int samples[], int x, int y)
			throws ImageReadException, IOException
	{
		int fBitsPerPixel = bitsPerSample[0];
		int colormap_scale = (1 << fBitsPerPixel);
		//			int expected_colormap_size = 3 * (1 << fBitsPerPixel);

		int index = samples[0];
		int red = fColorMap[index] >> 8;
		int green = fColorMap[index + (colormap_scale)] >> 8;
		int blue = fColorMap[index + (2 * colormap_scale)] >> 8;

		int alpha = 0xff;
		int rgb = (alpha << 24) | (red << 16) | (green << 8) | (blue << 0);
		bi.setRGB(x, y, rgb);

	}
}