export LC_ALL=C.UTF-8
export LANG=C.UTF-8
export EPOCHS=5
python train-single-model.py --epochs=$EPOCHS --dataset_path="/home/ubuntu/datasets/dataset-cronutils_en2en-250000-set01.psv"
python train-single-model.py --epochs=$EPOCHS --dataset_path="/home/ubuntu/datasets/dataset-cronutils_en2en-250000-set02.psv"
python train-single-model.py --epochs=$EPOCHS --dataset_path="/home/ubuntu/datasets/dataset-cronutils_en2en-250000-set03.psv"
python train-single-model.py --epochs=$EPOCHS --dataset_path="/home/ubuntu/datasets/dataset-cronutils_en2en-250000-set04.psv"
