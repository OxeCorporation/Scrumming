/* Plax version 1.2 */
/*
  Copyright (c) 2011 Cameron McEfee

  Permission is hereby granted, free of charge, to any person obtaining
  a copy of this software and associated documentation files (the
  "Software"), to deal in the Software without restriction, including
  without limitation the rights to use, copy, modify, merge, publish,
  distribute, sublicense, and/or sell copies of the Software, and to
  permit persons to whom the Software is furnished to do so, subject to
  the following conditions:

  The above copyright notice and this permission notice shall be
  included in all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
(function(e){function h(e){return Math.min.apply({},e)}function p(e){return Math.max.apply({},e)}function d(){return window.DeviceMotionEvent!=undefined}function v(t){if((new Date).getTime()<r+n)return;if(d()){var i=t.accelerationIncludingGravity,s=i.x,o=i.y;c.xArray.length>=5&&c.xArray.shift(),c.yArray.length>=5&&c.yArray.shift(),c.xArray.push(s),c.yArray.push(o),c.xMotion=Math.round((p(c.xArray)-h(c.xArray))*1e3)/1e3,c.yMotion=Math.round((p(c.yArray)-h(c.yArray))*1e3)/1e3,(c.xMotion>1.5||c.yMotion>1.5)&&a!=10&&(a=10),c.xMotion>f||c.yMotion>f?l++:l=0,l>=5?(u=!0,e(document).unbind("mousemove.plax"),e(window).bind("devicemotion",m(t))):(u=!1,e(window).unbind("devicemotion"),e(document).bind("mousemove.plax",function(e){m(e)}))}}function m(e){if((new Date).getTime()<r+n)return;r=(new Date).getTime();var t=e.pageX,f=e.pageY;if(u==1){var l=window.orientation?(window.orientation+180)%360/90:2,c=e.accelerationIncludingGravity,h=l%2==0?-c.x:c.y,p=l%2==0?c.y:c.x;t=l>=2?h:-h,f=l>=2?p:-p,t=(t+a)/2,f=(f+a)/2,t<0?t=0:t>a&&(t=a),f<0?f=0:f>a&&(f=a)}var d=t/(u==1?a:s),v=f/(u==1?a:o),m,l;for(l=i.length;l--;)m=i[l],m.invert!=1?m.obj.css("left",m.startX+m.xRange*d).css("top",m.startY+m.yRange*v):m.obj.css("left",m.startX-m.xRange*d).css("top",m.startY-m.yRange*v)}var t=25,n=1/t*1e3,r=(new Date).getTime(),i=[],s=e(window).width(),o=e(window).height(),u=!1,a=1,f=.05,l=0,c={xArray:[0,0,0,0,0],yArray:[0,0,0,0,0],xMotion:0,yMotion:0};e(window).resize(function(){s=e(window).width(),o=e(window).height()}),e.fn.plaxify=function(t){return this.each(function(){var n={xRange:0,yRange:0,invert:!1};for(var r in t)n[r]==0&&(n[r]=t[r]);n.obj=e(this),n.startX=this.offsetLeft,n.startY=this.offsetTop,n.invert==0?(n.startX-=Math.floor(n.xRange/2),n.startY-=Math.floor(n.yRange/2)):(n.startX+=Math.floor(n.xRange/2),n.startY+=Math.floor(n.yRange/2)),i.push(n)})},e.plax={enable:function(){e(document).bind("mousemove.plax",function(e){m(e)}),d()&&(window.ondevicemotion=function(e){v(e)})},disable:function(){e(document).unbind("mousemove.plax"),window.ondevicemotion=undefined}},typeof ender!="undefined"&&e.ender(e.fn,!0)})(function(){return typeof jQuery!="undefined"?jQuery:ender}());