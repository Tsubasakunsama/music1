<script>
import{renderer}from "../components/threejs/3d.js";
import { camera } from "../components/threejs/3d.js";
import {GUI} from 'three/examples/jsm/libs/lil-gui.module.min';
import { labelRenderer } from "../components/threejs/3d.js";
import {controls}from "../components/threejs/3d"
import{group2}from "../components/threejs/3d.js"
import{scene} from "../components/threejs/3d.js";
import{computergroup} from "../components/threejs/3d.js";
import{gpsgroup} from "../components/threejs/3d.js";
import{subgroup} from "../components/threejs/3d.js";
import{group1} from "../components/threejs/3d.js";
import{boatgroup} from "../components/threejs/3d.js";
import TWEEN from '@tweenjs/tween.js';
import {subDiv} from "../components/threejs/3d.js";
import {boatDiv} from "../components/threejs/3d.js";
import request from "@/utils/request";

export default {
  name: "3d",
  data() {
    return {
      gui: null,
    };

  },
  mounted() {
    this.$refs.webgl.appendChild(renderer.domElement);
    this.initgui();
    this.$refs.webgl.appendChild(labelRenderer.domElement);
    window.addEventListener("revise", () => {
      camera.aspect = window.innerWidth / window.innerHeight;
      camera.updateProjectionMatrix();
      renderer.setSize(window.innerWidth, window.innerHeight);
    });//监听屏幕大小修改宽高比例
  },
  destroyed() {
    this.gui.destroy();


  },
  methods: {
    moveboat:function() {
      request.get('/b/search').then(res=>{
        if(res.code==='0') {
          this.$message({
            message: "数据加载成功",
            type: "success",
          });
          let l = 0; // 从第一个数据开始显示
          group1.position.set(3,0,4);
          const tween1 = new TWEEN.Tween(group1.position);//创建一段tween动画
          tween1.to({x:res.data[l].x,y:res.data[l].y,z:res.data[l].z}, 5000).easing(TWEEN.Easing.Quadratic.InOut)
              .onUpdate(() => {
                // 在每一帧更新时，更新标签的内容以显示当前位置
                boatDiv.innerHTML = "x:" + group1.position.x.toFixed(2) + "\n" + // 使用toFixed(2)来格式化小数，保留两位
                    "y:" + group1.position.y.toFixed(2) + "\n" +
                    "z:" + group1.position.z.toFixed(2) + "\n";
              });
          //tween动画开始执行
          tween1.start();
          function render() {
            TWEEN.update();
            renderer.render(scene, camera);
            requestAnimationFrame(render);
          }
          render();
          group1.position.set(res.data[l].x,res.data[l].y,res.data[l].z);
          boatDiv.innerHTML = "x:" + group1.position.x + "\t" + "y:" + group1.position.y + "\t" + "z:" + group1.position.z + "\t";
          function updateDisplay() {
            // 检查是否还有数据要显示
            if (l+1< res.data.length) {
              // 更新comDiv的内容
              group1.position.set(res.data[l].x,res.data[l].y,res.data[l].z);
              const tween = new TWEEN.Tween(group1.position);//创建一段tween动画
              tween.to({x:res.data[l+1].x,y:res.data[l+1].y,z:res.data[l+1].z}, 5000).easing(TWEEN.Easing.Quadratic.InOut)
                  .onUpdate(() => {
                    // 在每一帧更新时，更新标签的内容以显示当前位置
                    boatDiv.innerHTML = "x:" + group1.position.x.toFixed(2) + "\n" + // 使用toFixed(2)来格式化小数，保留两位
                        "y:" + group1.position.y.toFixed(2) + "\n" +
                        "z:" + group1.position.z.toFixed(2) + "\n";
                  });
              //tween动画开始执行
              tween.start();
              function render() {
                TWEEN.update();
                renderer.render(scene, camera);
                requestAnimationFrame(render);
              }
              render();
              group1.position.set(res.data[l+1].x,res.data[l+1].y,res.data[l+1].z);
              boatDiv.innerHTML = "x:" + group1.position.x + "\t" + "y:" + group1.position.y + "\t" + "z:" + group1.position.z + "\t";
              // 准备显示下一条数据
              l++;
            } else {
              // 如果所有数据都已显示，可以清空comDiv或者显示一条消息表示没有更多数据
              boatDiv.innerHTML = "x:" + group1.position.x + "\t" + "y:" + group1.position.y + "\t" + "z:" + group1.position.z + "\t";
              // 停止定时器
              clearInterval(displayInterval);
            }
          }
          // 使用setInterval每秒调用一次updateDisplay函数
          const displayInterval = setInterval(updateDisplay, 5000);
        }
        else{
          this.$message({
            message: res.msg,
            type: 'error',
          });
        }
      })
    },
    movesub:function() {
      request.get('/s/search').then(res=>{
        if(res.code==='0') {
          this.$message({
            message: "数据加载成功",
            type: "success",
          });
          let l = 0; // 从第一个数据开始显示
          group2.position.set(0,-14,0);
          const tween = new TWEEN.Tween(group2.position);//创建一段tween动画
          tween.to({x:res.data[l].x,y:res.data[l].y,z:res.data[l].z}, 5000).easing(TWEEN.Easing.Quadratic.InOut)
              .onUpdate(() => {
                // 在每一帧更新时，更新标签的内容以显示当前位置
                subDiv.innerHTML = "x:" + group2.position.x.toFixed(2) + "\n" + // 使用toFixed(2)来格式化小数，保留两位
                    "y:" + group2.position.y.toFixed(2) + "\n" +
                    "z:" + group2.position.z.toFixed(2) + "\n";
              });
          //tween动画开始执行
          tween.start();
          function render() {
            TWEEN.update();
            renderer.render(scene, camera);
            requestAnimationFrame(render);
          }
          render();
          group2.position.set(res.data[l].x,res.data[l].y,res.data[l].z);
          subDiv.innerHTML = "x:" + group2.position.x + "\n" + "y:" + group2.position.y + "\n" + "z:" + group2.position.z + "\n";
          // 准备显示下一条数据
          function updateDisplay() {
            // 检查是否还有数据要显示
            if (l+1< res.data.length) {
              // 更新comDiv的内容
              group2.position.set(res.data[l].x,res.data[l].y,res.data[l].z);
              const tween2 = new TWEEN.Tween(group2.position);//创建一段tween动画
              tween2.to({x:res.data[l+1].x,y:res.data[l+1].y,z:res.data[l+1].z}, 5000).easing(TWEEN.Easing.Quadratic.InOut)
                  .onUpdate(() => {
                    // 在每一帧更新时，更新标签的内容以显示当前位置
                    subDiv.innerHTML = "x:" + group2.position.x.toFixed(2) + "\n" + // 使用toFixed(2)来格式化小数，保留两位
                        "y:" + group2.position.y.toFixed(2) + "\n" +
                        "z:" + group2.position.z.toFixed(2) + "\n";
                  });
              //tween动画开始执行
              tween2.start();
              function render() {
                TWEEN.update();
                renderer.render(scene, camera);
                requestAnimationFrame(render);
              }
              render();
              group2.position.set(res.data[l+1].x,res.data[l+1].y,res.data[l+1].z);
              subDiv.innerHTML = "x:" + group2.position.x + "\n" + "y:" + group2.position.y + "\n" + "z:" + group2.position.z + "\n";
              // 准备显示下一条数据
              l++;
            } else {
              // 如果所有数据都已显示，可以清空subDiv或者显示一条消息表示没有更多数据
              subDiv.innerHTML = "x:" + group2.position.x + "\n" + "y:" + group2.position.y + "\n" + "z:" + group2.position.z + "\n";
              // 停止定时器
              clearInterval(displayInterval);
            }
          }
          // 使用setInterval每秒调用一次updateDisplay函数
          const displayInterval = setInterval(updateDisplay, 5000);
        }
        else{
          this.$message({
            message: res.msg,
            type: 'error',
          });
        }
      })
    },
    initgui() {
      this.gui = new GUI();
      let eventObj = {
        Fullscreen: function () {
          document.body.requestFullscreen();
          console.log("全屏");
        },
        ExitFullscreen: function () {
          document.exitFullscreen();
          console.log("退出全屏");
        },
        turnback: function () {
          window.location.href = '/layout';
        }
      };
      let cameraposition = {
        waterup: function () {
          camera.position.set(-10, 10, 20);
          controls.target.set(0, 0, 0);
          controls.update();
        },
        waterdown: function () {
          camera.position.set(8, -7, 10);
          controls.target.set(6, -10, 3);
          controls.update();

        },
        return: function () {
          camera.position.set(-10,10,20)
          group1.position.set(3, 0, 4);
          group2.position.set(0, -14, 0);
          boatDiv.innerHTML="x:" + group1.position.x + "\n" + "y:" + group1.position.y + "\n" + "z:" + group1.position.z + "\n";
          subDiv.innerHTML="x:" + group2.position.x + "\n" + "y:" + group2.position.y + "\n" + "z:" + group2.position.z + "\n";
          controls.target.set(0, 0, 0);
          controls.update();
        }
      };
      let view = {
        main: function () {
          camera.position.set(-5, 10, 20);
          controls.target.set(0, 0, 0);
          controls.update();

        },
        top: function () {
          camera.position.set(-10, 30, 20);
          controls.target.set(-10, 0, 20);
          controls.update();
        },
        bottom: function () {
          camera.position.set(-10, -30, 20);
          controls.target.set(-10, -15, 20);
          controls.update();
        },
        aside: function () {
          camera.position.set(20, 0, 0);
          controls.target.set(0, 0, 0);
          controls.update();
        },
      }
      let see = {
        seecomputer: function () {
          group1.clear();
          group2.clear();
          group2.add(computergroup);

        },
        seegps: function () {
          group1.clear();
          group2.clear();
          group2.add(gpsgroup);
        },
        seesubmarine: function () {
          group1.clear();
          group2.clear();
          group2.add(subgroup);

        },
        seeboat: function () {
          group1.clear();
          group2.clear();
          group1.add(boatgroup);

        },
        seeall: function () {
          group2.add(subgroup);
          group2.add(gpsgroup);
          group2.add(computergroup);
          group1.add(boatgroup);
          camera.position.set(-5, 5, 20);
          controls.target.set(0, 0, 0);
          controls.update();
        },
      }
      let move = {
        moveboat: function () {
          const tween = new TWEEN.Tween(group1.position);//创建一段tween动画
          tween.to({x: 8, y: 0, z: 10}, 5000).easing(TWEEN.Easing.Quadratic.InOut)
              .onUpdate(() => {
                // 在每一帧更新时，更新标签的内容以显示当前位置
                boatDiv.innerHTML = "x:" + group1.position.x.toFixed(2) + "\n" + // 使用toFixed(2)来格式化小数，保留两位
                    "y:" + group1.position.y.toFixed(2) + "\n" +
                    "z:" + group1.position.z.toFixed(2) + "\n";
              });
          //tween动画开始执行
          tween.start();

          function render() {
            TWEEN.update();
            renderer.render(scene, camera);
            requestAnimationFrame(render);
          }

          render();
          group1.position.set(8, 0, 10);
          boatDiv.innerHTML = "x:" + group1.position.x + "\n" + "y:" + group1.position.y + "\n" + "z:" + group1.position.z + "\n";
        },
        movesub: function () {
          const tween2 = new TWEEN.Tween(group2.position);//创建一段tween动画
          tween2.to({x: 8, y: -15, z: 10}, 5000).easing(TWEEN.Easing.Quadratic.InOut)
              .onUpdate(() => {
                // 在每一帧更新时，更新标签的内容以显示当前位置
                subDiv.innerHTML = "x:" + group2.position.x.toFixed(2) + "\n" + // 使用toFixed(2)来格式化小数，保留两位
                    "y:" + group2.position.y.toFixed(2) + "\n" +
                    "z:" + group2.position.z.toFixed(2) + "\n";
              });
          //tween动画开始执行
          tween2.start();
          const tween3 = new TWEEN.Tween(group2.rotation);//创建一段tween动画
          tween3.to({x: Math.PI / 12, y: -Math.PI / 12, z: 0}, 2000);
          //tween动画开始执行
          tween3.start();

          function render() {
            TWEEN.update();
            renderer.render(scene, camera);
            requestAnimationFrame(render);
          }

          render();
          group2.position.set(9, -15, 10);
          subDiv.innerHTML = "x:" + group2.position.x + "\n" + "y:" + group2.position.y + "\n" + "z:" + group2.position.z + "\n";
        }
      }

      this.gui.add(eventObj, "Fullscreen").name("全屏");
      this.gui.add(eventObj, "ExitFullscreen").name("退出全屏");
      this.gui.add(eventObj, "turnback").name("返回主页面");
      let folder = this.gui.addFolder("相机位置");
      folder.add(cameraposition, "waterup").name("水上");
      folder.add(cameraposition, "waterdown").name("水下");
      folder.add(cameraposition, "return").name("复原");
      folder.add(camera.position, "x", -100, 100).name("x轴");
      folder.add(camera.position, "y", -100, 100).name("y轴");
      folder.add(camera.position, "z", -100, 100).name("z轴");
      let folder2 = this.gui.addFolder("视图");
      folder2.add(view, "main").name("主视");
      folder2.add(view, "top").name("俯视");
      folder2.add(view, "bottom").name("仰视");
      folder2.add(view, "aside").name("侧视");
      let folder3 = this.gui.addFolder("只显示模型");
      folder3.add(see, "seecomputer").name("电脑");
      folder3.add(see, "seesubmarine").name("潜艇");
      folder3.add(see, "seegps").name("gps");
      folder3.add(see, "seeboat").name("船");
      folder3.add(see, "seeall").name("全部显示");
      let folder4 = this.gui.addFolder("模型轨迹");
      folder4.add(this, "moveboat").name("船轨迹");
      folder4.add(this, "movesub").name("水下船轨迹");

    }
  }
}


</script>


<template>
      <span ref="webgl" class="canvas" ></span>

</template>

<style scoped>


::v-deep .label {
  box-shadow: 0 0 2px #00ffff inset;
  background: linear-gradient(#00ffff, #00ffff) left top;
  background-repeat: no-repeat;
  background-size: 1px 6px, 6px 1px;
  background-color: rgba(0, 0, 0, 0.8);
  color: #ffffff;
  font-size: 12px;
  padding: 4px 10px;
  position: absolute;
  z-index: 9;
  display: inline;
 
}

*{

padding:0;
margin:0;

}
.canvas{
  position:absolute;
  overflow:hidden;
  top:60px;
  left:0px;
}


</style>
