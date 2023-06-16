###### SQL DIAG：慢SQL发现

**功能描述**

SQLdiag是一个SQL语句执行时间预测工具，通过模板化方法或者深度学习方法，实现在不获取SQL语句执行计划的前提下，依据语句逻辑相似度与历史执行记录，预测SQL语句的执行时间并以此发现异常SQL。

**语法格式**

```
gs_dbmind component sqldiag --help 
```

```
usage:   [-h] [-f CSV_FILE] [--predicted-file PREDICTED_FILE]
               [--model {template,dnn}] --model-path MODEL_PATH
               [--config-file CONFIG_FILE]
               {train,predict,finetune}

SQLdiag integrated by openGauss.

positional arguments:
  {train,predict,finetune}
                        The training mode is to perform feature extraction and
                        model training based on historical SQL statements. The
                        prediction mode is to predict the execution time of a
                        new SQL statement through the trained model.

optional arguments:
  -h, --help            show this help message and exit
  -f CSV_FILE, --csv-file CSV_FILE
                        The data set for training or prediction. The file
                        format is CSV. If it is two columns, the format is
                        (SQL statement, duration time). If it is three
                        columns, the format is (timestamp of SQL statement
                        execution time, SQL statement, duration time).
  --predicted-file PREDICTED_FILE
                        The file path to save the predicted result.
  --model {template,dnn}
                        Choose the model model to use.
  --model-path MODEL_PATH
                        The storage path of the model file, used to read or
                        save the model file.
  --config-file CONFIG_FILE
```

**参数说明**

| 参数            | 参数说明           | 取值范围      |
| :-------------- | :----------------- | :------------ |
| -f              | 训练或预测文件位置 | -             |
| –predicted-file | 预测结果存储位置   | -             |
| –model          | 模型选择           | template, dnn |
| –model-path     | 训练模型存储位置   | -             |

**注意事项**

- 需要保证用户提供训练数据。
- 如果用户通过提供的工具收集训练数据，则需要启用WDR功能，涉及到的参数为track_stmt_stat_level和log_min_duration_statement，具体情况见下面小结。
- 为保证预测准确率，用户提供的历史语句日志应尽可能全面并具有代表性。

**SQL流水采集方法**

本工具需要用户提前准备数据，训练数据格式如下，每个样本通过换行符分隔：

```
SQL,EXECUTION_TIME
```

预测数据格式如下：

```
SQL
```

其中SQL表示SQL语句的文本，EXECUTION_TIME表示SQL语句的执行时间，样例数据见sample_data中的train.csv和predict.csv。

用户可以按照要求格式自己收集训练数据，工具也提供了脚本自动采集（load_sql_from_rd），该脚本基于WDR报告获取SQL信息，涉及到的参数有log_min_duration_statement和track_stmt_stat_level：

- 其中log_min_duration_statement表示慢SQL阈值，如果为0则全量收集，时间单位为毫秒；
- track_stmt_stat_level表示信息捕获的级别，建议设置为track_stmt_stat_level='L0,L0'

参数开启后，可能占用一定的系统资源，但一般不大。持续的高并发场景可能产生5%以内的损耗，数据库并发较低的场景，性能损耗可忽略。下述脚本存在于sqldiag根目录（$GAUSSHOME/bin/components/sqldiag）中。

使用脚本获取训练集方式：

```
load_sql_from_wdr.py [-h] --port PORT --start_time START_TIME
                            --finish_time FINISH_TIME [--save_path SAVE_PATH]
```

**操作步骤**

1、提供历史日志以供模型训练

2、进行训练与预测操作。

基于模板法的训练与预测：

```
 gs_dbmind component sqldiag [train, predict] -f FILE --model template --model-path template_model_path 
```

基于DNN的训练与预测：

```
 gs_dbmind component sqldiag [train, predict] -f FILE --model dnn --model-path dnn_model_path
```

**示例**

- 使用提供的测试数据进行模板化训练：

```
gs_dbmind component sqldiag train -f ./sample_data/train.csv --model template --model-path ./template 
```

- 使用提供的测试数据进行模板化预测：

```
gs_dbmind component sqldiag predict -f ./sample_data/predict.csv --model template --model-path ./template --predicted-file ./result/t_result
```

- 使用提供的测试数据进行模板化模型更新：

```
gs_dbmind component sqldiag finetune -f ./sample_data/train.csv --model template --model-path ./template 
```

- 使用提供的测试数据进行DNN训练：

```
gs_dbmind component sqldiag train -f ./sample_data/train.csv --model dnn --model-path ./dnn_model 
```

- 使用提供的测试数据进行DNN预测：

```
gs_dbmind component sqldiag predict -f ./sample_data/predict.csv --model dnn --model-path ./dnn_model --predicted-file 
```

- 使用提供的测试数据进行DNN模型更新：

```
gs_dbmind component sqldiag finetune -f ./sample_data/train.csv --model dnn --model-path ./dnn_model
```

**常见问题**

- 训练场景失败：请检查历史日志文件路径是否正确，且文件格式符合上文规定。
- 预测场景失败：请检查模型路径是否正确。确保待预测负载文件格式正确。