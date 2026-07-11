import { useState } from "react";
import { createReportItem } from "./report_api";

type Props = {
  onClose: () => void;
};

function ReportItemModal({ onClose }: Props) {
  const storedUser = localStorage.getItem("user");
  const user = storedUser ? JSON.parse(storedUser) : null;

  const [reportType, setReportType] = useState<"LOST" | "FOUND">("LOST");
  const [itemName, setItemName] = useState("");
  const [category, setCategory] = useState("");
  const [description, setDescription] = useState("");
  const [location, setLocation] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [imageFile, setImageFile] = useState<File | null>(null);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files || !e.target.files[0]) return;
    setImageFile(e.target.files[0]);
  };

  const handleSubmit = async () => {
    if (!user?.id) {
      alert("You must be logged in first.");
      return;
    }

    if (!itemName || !category || !description || !location) {
      alert("Please complete all required fields.");
      return;
    }

    try {
      await createReportItem({
        userId: user.id,
        reportType,
        itemName,
        category,
        description,
        location,
        imageFile,
        imageUrl,
      });

      alert("Item reported successfully!");
      onClose();
    } catch (err) {
      console.error(err);
      alert("Failed to submit report.");
    }
  };

  return (
    <div className="report-modal-overlay">
      <div className="report-modal">
        <button className="report-close-btn" onClick={onClose}>
          ×
        </button>

        <h2>Report Item</h2>

        <div className="report-type-buttons">
          <button
            type="button"
            className={reportType === "LOST" ? "active" : ""}
            onClick={() => {
              setReportType("LOST");
              setLocation("");
            }}
          >
            Lost Item
          </button>

          <button
            type="button"
            className={reportType === "FOUND" ? "active" : ""}
            onClick={() => {
              setReportType("FOUND");
              setLocation("");
            }}
          >
            Found Item
          </button>
        </div>

        <p>
          Fill in the details below to report a{" "}
          {reportType === "LOST" ? "lost" : "found"} item.
        </p>

        <label>Name of Item</label>
        <input
          type="text"
          placeholder="Example: Black Wallet"
          value={itemName}
          onChange={(e) => setItemName(e.target.value)}
        />

        <label>Category</label>
        <select value={category} onChange={(e) => setCategory(e.target.value)}>
          <option value="">Select Category</option>
          <option>Gadget</option>
          <option>Clothing</option>
          <option>Bag</option>
          <option>Wallet</option>
          <option>ID / Documents</option>
          <option>Books</option>
          <option>School Supplies</option>
          <option>Keys</option>
          <option>Umbrella</option>
          <option>Jewelry</option>
          <option>Accessories</option>
          <option>Sports Equipment</option>
          <option>Water Bottle</option>
          <option>Electronics</option>
          <option>Others</option>
        </select>

        <label>Description</label>
        <textarea
          placeholder="Describe your item..."
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />

        <label>
          {reportType === "LOST" ? "Last Seen Location" : "Found Location"}
        </label>
        <input
          type="text"
          placeholder={
            reportType === "LOST"
              ? "Library, Canteen, Room 301..."
              : "Where did you find the item?"
          }
          value={location}
          onChange={(e) => setLocation(e.target.value)}
        />

        <label>Item Image (Optional)</label>

        <label htmlFor="upload-image" className="upload-box">
          <div className="upload-icon">📷</div>

          <h3>Upload Image</h3>

          <p>Click here to choose an image</p>

          {imageFile && <div className="selected-file">✔ {imageFile.name}</div>}
        </label>

        <input
          id="upload-image"
          type="file"
          accept="image/*"
          hidden
          onChange={handleImageChange}
        />

        <div className="or-divider">OR</div>

        <input
          type="text"
          placeholder="Paste image URL (optional)"
          value={imageUrl}
          onChange={(e) => setImageUrl(e.target.value)}
        />

        <button className="report-submit-btn" onClick={handleSubmit}>
          Submit Report
        </button>
      </div>
    </div>
  );
}

export default ReportItemModal;