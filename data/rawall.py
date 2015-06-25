import os
import jieba
import codecs
def readAndwrite(path):

    fileList=[]
    files=os.listdir(path)
    for f in files:
        if(os.path.isdir(path+'\\'+f)):
            pass
        if(os.path.isfile(path+'\\'+f)):
            fileList.append(f)
            a=path+'\\'+f
            file_object=open(a,'r')
            try:
                all_the_text=file_object.read()
                att=jieba.cut(all_the_text,cut_all=False)
                output=codecs.open(path+'\\'+'after'+f,'w','utf-8')
                output.write(' '.join(att))
                output.close()
            finally:
                file_object.close()


if __name__ == '__main__':  
    Path='C:\\Users\\Administrator.Y26OC25YCRAPBLH\\workspace\\MyLDA\\data\\original data'
    readAndwrite(Path)


    
