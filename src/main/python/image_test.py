import sys
from ultralytics import YOLO


def get_image_details(image_path):
    detected_items = []

    # Load model
    model_path = r"C:\Users\josli\Desktop\RecipeServer\src\main\python\best.pt"
    model = YOLO(model_path)

    # Run inference
    results = model(image_path, verbose=False)

    # Extract class names
    for result in results:
        if result.boxes is not None:
            for box in result.boxes:
                cls_id = int(box.cls[0].item())
                class_name = model.names[cls_id].strip().lower()
                if class_name:  # Skip empty strings
                    detected_items.append(class_name)

    detected_items = list(set(detected_items))
    return detected_items


if __name__ == "__main__":
    try:
        details = get_image_details(r"C:\Users\josli\Desktop\RecipeServer\uploads\image.jpg")
        print(details)
    except Exception as e:
        print(f"Error: {e}")
