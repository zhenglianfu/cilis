/**
* !use TDM-GCC 4.8.1 
* env window7 pro
* power by dev-c++
*/
#include <stdio.h>
#include <string.h>
#include <sys/types.h>   
#include <dirent.h>
#define BUFF_SIZE 2048
#define BUFF_LITE_SIZE 256
FILE* read_file(char* path);
int read_line(FILE* stream);
int copydir(char* source, char* target);
int copyfile(char* source, char* target);

int main(int argc, char* argv[]){
	FILE *fp = read_file("blank.txt");
	if (fp == NULL){
		printf("file is not found");
		return -1;
	}
	read_line(fp);
	fclose(fp);
	copyfile("main_banner_11.png", "main_banner_11_temp.png");
	return 0;
}

FILE* read_file(char* path) {
	FILE *fp = fopen(path, "rt");
	return fp;
}

int read_line(FILE *stream){
	char buff[BUFF_SIZE];
	int  line_num = 1;
	while(fgets(buff, BUFF_SIZE, stream)){
		printf("line%d: %s", line_num, buff);
		line_num += 1;
	}
	return 0;
}

int copydir(char* source, char* target){
	DIR* srcdir = opendir(source);
	DIR* tdir   = opendir(target);
	if (srcdir == NULL) {
		printf("directory %s is not existed\n", source);
		return -1;
	}
	if (access(target, 0) != 0) {
		mkdir(target);
	}
	return 0;
}

int copyfile(char* source, char* target){
	// 使用二进制方式读写文件，适用于复制各种文件 
	if(strcmp(source, target) == 0){
		printf("do not need copy");
		return -2;
	} 
	char buff[BUFF_LITE_SIZE];
	FILE *src = fopen(source, "rb");
	if (src == NULL) {
		printf("file %s is not existed\n", src);
		return -1;
	}
	FILE *t   = fopen(target, "wb+");	
	while(!feof(src)){
		fread(buff, BUFF_LITE_SIZE, 1, src);
		fwrite(buff, BUFF_LITE_SIZE, 1, t);
	}
	fclose(src);
	fclose(t);
}


