/**
* !use TDM-GCC 4.8.1 
* env window7 pro
* power by dev-c++
*/
#include <stdio.h>
#include <string.h>
#include <sys/types.h>   
#include <dirent.h>
// constant
#define BUFF_SIZE 2048
#define BUFF_LITE_SIZE 256

FILE* read_file(char* path);
int read_line(FILE* stream);
int copydir(char* source, char* target);
int copyfile(char* source, char* target);
char* getParentPath(char* path, char* dir);

int main(int argc, char* argv[]){
	copydir("e:/c", "e:/c");
	char* p = getParentPath("/name.txt", "e:/c/copy");
	printf("%s", p);
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
	// if targte dir is not existed, create it
	if (access(target, 0) != 0) {
		mkdir(target);
	}
	// read dir 
	struct dirent *ptr;
	while((ptr = readdir(srcdir)) != NULL){
		// 跳过当前目录和父目录 
		if (strcmp(ptr->d_name, ".") == 0 || strcmp(ptr->d_name, "..") == 0) {
			continue;
		}
		// 其中程序中win不支持文件类型（d_type），可以根据文件名称后缀来判断文件类型；linux可以直接使用d_type判断是目录还是文件。
		#ifdef _WIN32
			printf("d_name: %s\n", ptr->d_name);
		#endif
		#ifdef __linux
            printf("d_type:%d d_name: %s\n", ptr->d_type,ptr->d_name);
        #endif
	}
	closedir(srcdir);
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

char* getParentPath(char* path, char* dir){
	while (path[0] == '.'){
		if (strstr(path, "..") != NULL){
			int count = strlen(path) - 3;
			char temp[count];
			strncpy(temp, strstr(path, "../"), count);
			path = temp;
			printf("path: if ../ %s", path);
		} else if (strstr(path, "./") != NULL){
			int count = strlen(path) - 2;
			char temp[count];
			strncpy(temp, strstr(path, "./"), count);
			path = temp;
			printf("path: if ./ %s", path);
		} else {
			int count = strlen(path) - 1;
			char temp[count];
			strncpy(temp, strchr(path, '.'), count);
			path = temp;
			printf("path: if . %s", path);
		}	
	}
	if (strchr(path, '/') != NULL){
		printf("if / path: %s\n", path);
	} else{
		
	}
	char dest[strlen(path) + strlen(dir)];
	char* ret = dest;
	strcpy(ret, dir);
	strcat(ret, path);
	return ret;
}


