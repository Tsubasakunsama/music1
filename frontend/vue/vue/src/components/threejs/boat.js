import * as THREE from 'three';
import {OrbitControls} from "three/examples/jsm/controls/OrbitControls";
import {GLTFLoader} from "three/examples/jsm/loaders/GLTFLoader"
import { DRACOLoader } from 'three/examples/jsm/loaders/DRACOLoader';

//初始化场景
const scene=new THREE.Scene();
scene.background = new THREE.Color("0xffffff");
const camera=new THREE.PerspectiveCamera(75,window.innerWidth/window.innerHeight,0.1,2000);//透视相机
camera.position.set(5,5,13);//设置摄像头位置
camera.aspect=window.innerWidth/window.innerHeight;//设置摄像头宽高比
camera.updateProjectionMatrix();//更新摄像头投影矩阵
scene.add(camera);


const renderer=new THREE.WebGLRenderer({
  antialias:true,//抗锯齿

});//初始化渲染器
renderer.outputEncoding=THREE.sRGBEncoding;
renderer.setSize(window.innerWidth,window.innerHeight);//设置宽高
renderer.toneMapping=THREE.ACESFilmicToneMapping;
renderer.toneMappingExposure=0.5;

window.addEventListener("revise",()=>{
  camera.aspect=window.innerWidth/window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth,window.innerHeight);
});//监听屏幕大小修改宽高比例

const controls=new OrbitControls(camera,renderer.domElement);//实例化控制器

const dracoLoader= new DRACOLoader();
dracoLoader.setDecoderPath("/draco/");
const gltfLoader=new GLTFLoader();
gltfLoader.setDRACOLoader(dracoLoader);

//创建加载指示器
const loadingIndicatorGeometry = new THREE.BoxGeometry(1, 1);
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


gltfLoader.load("/model/tow_boat.glb",(gltf)=>{
  const model=gltf.scene;
  scene.add(model);
    // 移除加载指示器
    scene.remove(loadingIndicator);
    // 停止渲染加载指示器
    cancelAnimationFrame(animateLoadingIndicator);

});
fetch('/model/tow_boat.glb')
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
var directionalLight = new THREE.DirectionalLight(0xffeedd,4.0);
   directionalLight.position.set(0, 10, 0);
   scene.add(directionalLight);
 var directionalLightb = new THREE.DirectionalLight(0xffeedd,4.0);
   directionalLightb.position.set(0, -10, 0);
   scene.add(directionalLightb);
 var directionalLightc = new THREE.DirectionalLight(0xffeedd,4.0);
   directionalLightb.position.set(0, 0, 10);
   scene.add(directionalLightc);

 function render(){
  renderer.render(scene,camera);//渲染场景
  requestAnimationFrame(render);//自动更新渲染器
}
render();






export{renderer};
