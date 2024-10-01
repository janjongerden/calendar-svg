/*
 * Copyright (c) 2020, nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package svglib.elements

import svglib.attributes.AttributeProperty
import svglib.attributes.AttributeType
import svglib.attributes.HasAttributes
import svglib.attributes.HasAttributesImpl
import svglib.attributes.HasStroke
import svglib.attributes.HasStrokeImpl

/**
 * An SVG line element.
 */
class LINE(validation: Boolean = false, hasAttributes: HasAttributes = HasAttributesImpl(validation)) :
    Element("line", validation, hasAttributes),
    HasStroke by HasStrokeImpl(hasAttributes) {

    /**
     * The X1 coordinate of the line.
     */
    var x1: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)

    /**
     * The Y1 coordinate of the line.
     */
    var y1: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)

    /**
     * The X2 coordinate of the line.
     */
    var x2: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)

    /**
     * The Y2 coordinate of the line.
     */
    var y2: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)
}
