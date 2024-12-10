import * as THREE from 'three';
import {OrbitControls} from "three/examples/jsm/controls/OrbitControls";
import{Water}from "three/examples/jsm/objects/Water2";
import road from "@/assets/1000.png";
import {GLTFLoader} from "three/examples/jsm/loaders/GLTFLoader"
import { DRACOLoader } from 'three/examples/jsm/loaders/DRACOLoader';
import { AxesHelper } from 'three';
import {CSS2DRenderer,CSS2DObject} from "three/examples/jsm/renderers/CSS2DRenderer.js";





//初始化场景
const scene=new THREE.Scene();
const camera=new THREE.PerspectiveCamera(75,window.innerWidth/window.innerHeight,0.1,2000);//透视相机
camera.position.set(-10,10,20);//设置摄像头位置
camera.aspect=window.innerWidth/window.innerHeight;//设置摄像头宽高比
camera.updateProjectionMatrix();//更新摄像头投影矩阵
scene.add(camera);


const renderer = new THREE.WebGLRenderer( );//初始化渲染器
renderer.outputColorSpace = THREE.SRGBColorSpace;
renderer.setSize(window.innerWidth,window.innerHeight);//设置宽高
renderer.toneMapping=THREE.ACESFilmicToneMapping;
renderer.toneMappingExposure=0.5;

const skyGeometry=new THREE.SphereGeometry(300,60,60);
const skyMaterial=new THREE.MeshBasicMaterial({
  map:new THREE.TextureLoader().load(road),
});
skyGeometry.scale(1,1,-1);
const sky=new THREE.Mesh(skyGeometry,skyMaterial);
scene.add(sky);//增加天空

const waterGeometry=new THREE.CircleGeometry(300,64);
const water=new Water(waterGeometry,{
    map:new THREE.TextureLoader().load("/textures/water/Water_2_M_Normal.jpg"),
      color: '#abcdef', // 水体颜色
      scale: 30, // 波浪大小
      flowDirection: new THREE.Vector2(1, 1), // 水流方向
      textureWidth: 1024, // 纹理宽度
      textureHeight: 1024, // 纹理高度
  });
water.rotation.x=-Math.PI/2;
water.position.y=-0.2;
scene.add(water);

const water2Geometry=new THREE.CircleGeometry(300,64);
const water2=new Water(water2Geometry,{
  map:new THREE.TextureLoader().load("/textures/water/Water_2_M_Normal.jpg"),
    color: '#abcdef', // 水体颜色
    scale: 30, // 波浪大小
    flowDirection: new THREE.Vector2(1, 1), // 水流方向
    textureWidth: 1024, // 纹理宽度
    textureHeight: 1024, // 纹理高度
});
water2.rotation.x=-Math.PI/2;
water2.position.y=-10;
scene.add(water2);//海底平面

const water3Geometry=new THREE.CircleGeometry(300,64);
const water3=new Water(water3Geometry,{
    color: '#abcdef', // 水体颜色
    scale: 3, // 波浪大小
    flowDirection: new THREE.Vector2(1, 1), // 水流方向
    textureWidth: 1024, // 纹理宽度
    textureHeight: 1024, // 纹理高度
});
water3.rotation.x=Math.PI/2;

scene.add(water3);//海底平面

  // const axes=new AxesHelper(50);
  // scene.add(axes);   //辅助坐标系

const dracoLoader= new DRACOLoader();
dracoLoader.setDecoderPath("/draco/glft/");
const gltfLoader=new GLTFLoader();
gltfLoader.setDRACOLoader(dracoLoader);


var directionalLight = new THREE.DirectionalLight(0xffeedd,4.0);
directionalLight.position.set(0, 10, 0);
scene.add(directionalLight);
var directionalLightb = new THREE.DirectionalLight(0xffeedd,4.0);
directionalLightb.position.set(0, -10, 0);
scene.add(directionalLightb);
var directionalLightc = new THREE.DirectionalLight(0xffeedd,4.0);
directionalLightb.position.set(0, 0, 10);
scene.add(directionalLightc);

const boatDiv = document.createElement('button');
boatDiv.className = 'label';
// 创建css2d对象。用CSS2DObject包装HTML
const boatLabel = new CSS2DObject(boatDiv);
// 设置2d对象的位置
boatLabel.position.set(0,0,0);


const group1=new THREE.Group();
const boatgroup=new THREE.Group();
gltfLoader.load( '/model/tow_boat/boat.gltf', function ( gltf ) {
    const boat = gltf.scene.getObjectByName("boat");
    console.log(boat);
    boat.scale.set(2, 2, 2);
    boatgroup.add(boat);
    group1.add(boatgroup);
    group1.position.set(3, 0, 4);
    boatDiv.innerHTML ="x:"+group1.position.x+"\n"+"y:"+group1.position.y+"\n"+"z:"+group1.position.z+"\n";
    scene.add(group1);
    renderer.domElement.addEventListener('click', function (event) {
        // .offsetY、.offsetX以canvas画布左上角为坐标原点,单位px
        const px = event.offsetX;
        const py = event.offsetY;
        //屏幕坐标px、py转WebGL标准设备坐标x、y
        //width、height表示canvas画布宽高度
        const x = (px / window.innerWidth) * 2 - 1;
        const y = -(py / window.innerHeight) * 2 + 1;
        //创建一个射线投射器`Raycaster`
        const raycaster = new THREE.Raycaster();
        //.setFromCamera()计算射线投射器`Raycaster`的射线属性.ray
        // 形象点说就是在点击位置创建一条射线，射线穿过的模型代表选中
        raycaster.setFromCamera(new THREE.Vector2(x, y), camera);
        const intersects1 = raycaster.intersectObjects(boat.children);
        // intersects.length大于0说明，说明选中了模型
        if (intersects1.length > 0) {
            group1.add(boatLabel);
        }
    });
});
fetch('/model/tow_boat/tow_boat.gltf')
    .then(response => response.blob())
    .then(blob => {
        const url = URL.createObjectURL(blob);
        gltfLoader.load(url, (gltf) => {
            const model = gltf.scene;
            scene.add(model);
            // 清理对象URL
            URL.revokeObjectURL(url);
        });
    })
    .catch(error => {
        console.error('Error loading model:', error);
    });
group1.position.set(7,0,8);
//创建标签
const subDiv = document.createElement('button');
subDiv.className = 'label';
const subLabel = new CSS2DObject(subDiv);
subLabel.position.set(0,6,0);

const comDiv = document.createElement('button');
comDiv.className = 'label';
comDiv.innerHTML = '水下设备信息';
const comLabel = new CSS2DObject(comDiv);
comLabel.position.set(0,0.2,0);

const gpsDiv = document.createElement('button');
gpsDiv.className = 'label';
gpsDiv.innerHTML = 'gps';
const gpsLabel = new CSS2DObject(gpsDiv);
subLabel.position.set(0,0.1,0);

const group2=new THREE.Group();
const computergroup=new THREE.Group();
const gpsgroup=new THREE.Group();
const subgroup=new THREE.Group();

gltfLoader.load( '/model/submarine/submarine.gltf', function ( gltf ) {
    const computer=gltf.scene.getObjectByName("computer");
    const submarine=gltf.scene.getObjectByName("submarine001");
    const gps=gltf.scene.getObjectByName("gps");
   computergroup.add(computer);
   subgroup.add(submarine);
   gpsgroup.add(gps);
   group2.add(computergroup,subgroup,gpsgroup);
   group2.scale.set(0.9,0.9,0.9);
   group2.position.set(0,-14,0);
   subDiv.innerHTML ="x:"+group2.position.x+"\n"+"y:"+group2.position.y+"\n"+"z:"+group2.position.z+"\n";
   scene.add(group2);
   renderer.domElement.addEventListener('click', function (event) {
        // .offsetY、.offsetX以canvas画布左上角为坐标原点,单位px
        const px = event.offsetX;
        const py = event.offsetY;
        //屏幕坐标px、py转WebGL标准设备坐标x、y
        //width、height表示canvas画布宽高度
        const x = (px / window.innerWidth) * 2 - 1;
        const y = -(py /window.innerHeight) * 2 + 1;
        //创建一个射线投射器`Raycaster`
        const raycaster = new THREE.Raycaster();
        //.setFromCamera()计算射线投射器`Raycaster`的射线属性.ray
        // 形象点说就是在点击位置创建一条射线，射线穿过的模型代表选中
        raycaster.setFromCamera(new THREE.Vector2(x, y), camera);
        //对参数中的网格模型对象进行射线交叉计算
        const intersects1 = raycaster.intersectObjects(submarine.children);
        const intersects2 = raycaster.intersectObjects(gps.children);
        const intersects3 = raycaster.intersectObjects(computer.children);
        // intersects.length大于0说明，说明选中了模型
        if (intersects1.length > 0) {
            group2.add(subLabel);
        }
        if (intersects2.length > 0) {
            group2.add(gpsLabel);
        }
        if (intersects3.length > 0) {
            group2.add(comLabel);
        }
    })


});
fetch('/model/submarine/submarine.gltf')
    .then(response => response.blob())
    .then(blob => {
        const url = URL.createObjectURL(blob);
        gltfLoader.load(url, (gltf) => {
            const model = gltf.scene;
            scene.add(model);
            // 清理对象URL
            URL.revokeObjectURL(url);
        });
    })
    .catch(error => {
        console.error('Error loading model:', error);
    });
//创建加载指示器
const loadingIndicatorGeometry = new THREE.BoxGeometry(10, 10,10);
const loadingIndicatorMaterial = new THREE.MeshBasicMaterial({
    color: 0x808080, // 灰色，表示加载中
    transparent: true,
    opacity: 0.8,
});
const loadingIndicator = new THREE.Mesh(loadingIndicatorGeometry, loadingIndicatorMaterial);
scene.add(loadingIndicator);
// 加载指示器的旋转动画
function animateLoadingIndicator() {
    loadingIndicator.rotation.z += 0.05; // 每帧旋转5度
    loadingIndicator.rotation.y += 0.05; // 每帧旋转5度
    loadingIndicator.rotation.x += 0.05; // 每帧旋转5度
    renderer.render(scene, camera);
    requestAnimationFrame(animateLoadingIndicator); // 请求下一帧
}

// 开始旋转加载指示器
animateLoadingIndicator();



const group3=new THREE.Group();
gltfLoader.load( '/model/dam11/dam.gltf', function ( gltf ) {
    const model=gltf.scene;
    group3.add(model);
    scene.add(group3);
    // 移除加载指示器
    scene.remove(loadingIndicator);
    // 停止渲染加载指示器
    cancelAnimationFrame(animateLoadingIndicator);

});



const labelRenderer = new CSS2DRenderer()
labelRenderer.setSize(window.innerWidth,innerHeight);
labelRenderer.domElement.style.position = 'absolute';
labelRenderer.domElement.style.top = '0px';
labelRenderer.domElement.style.left = '0px';
labelRenderer.domElement.style.zIndex = '1000';
labelRenderer.domElement.style.pointerEvents = 'none';
const controls = new OrbitControls(camera, renderer.domElement);

function render(){
    renderer.render(scene,camera);//渲染场景
    labelRenderer.render(scene,camera);
    requestAnimationFrame(render);//自动更新渲染器
}
render();

export{renderer};
export{camera};
export{labelRenderer};
export{controls}
export{group2};
export{scene};
export{computergroup};
export{gpsgroup};
export{subgroup};
export{group1};
export{boatgroup};
export{boatDiv};
export{subDiv};