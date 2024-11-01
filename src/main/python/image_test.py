import sys


def get_image_details(image_path):

    # todo
    # image processing here

    mylist = ["apple"]
    return mylist


if __name__ == "__main__":
    image_path = sys.argv[1]
    try:
        details = get_image_details(image_path)
        print(details)
    except Exception as e:
        print(f"Error: {e}")
