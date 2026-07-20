import { useEffect, useState } from "react";

import "../css/component_style.css";

import { updateReport } from "./report_api";

type ItemReport = {
  id: number;
  userId?: string;
  itemName?: string;
  reportType?: string;
  itemType?: string;
  category?: string;
  location?: string;
  lastSeenLocation?: string;
  description?: string;
  status?: string;
  imagePath?: string;
  imageUrl?: string;
  createdAt?: string;
};

type EditReportedItemModalProps = {
  item: ItemReport;

  onClose: () => void;
};

function EditReportedItemModal({

  item,

  onClose,

}: EditReportedItemModalProps) {

  const [itemName, setItemName] =
    useState("");

  const [category, setCategory] =
    useState("");

  const [description, setDescription] =
    useState("");

  const [location, setLocation] =
    useState("");

  const [isSaving, setIsSaving] =
    useState(false);

  const [errorMessage, setErrorMessage] =
    useState("");

  /*
   * ==========================================================
   * PREFILL FORM
   * ==========================================================
   */

  useEffect(() => {

    setItemName(
      item.itemName || ""
    );

    setCategory(
      item.category || ""
    );

    setDescription(
      item.description || ""
    );

    setLocation(
      item.location ||
      item.lastSeenLocation ||
      ""
    );

  }, [item]);

  /*
   * ==========================================================
   * UPDATE REPORT
   * ==========================================================
   */

  const handleUpdate = async () => {

    if (
      itemName.trim() === "" ||
      category.trim() === "" ||
      description.trim() === "" ||
      location.trim() === ""
    ) {

      setErrorMessage(
        "Please complete all fields."
      );

      return;

    }

    setErrorMessage("");

    setIsSaving(true);

    try {

      await updateReport(
        item.id,
        {
          itemName,
          category,
          description,
          location,
        }

      );

      onClose();

    } catch (error) {

      console.error(error);

      setErrorMessage(
        "Failed to update report."
      );

    } finally {

      setIsSaving(false);

    }

  };

    /*
   * ==========================================================
   * EDIT REPORT MODAL UI
   * ==========================================================
   */

  return (

    <div className="modal-overlay">
      <div className="report-modal">
        <div className="report-header">
          <h2>Edit Report</h2>
          <button
            className="close-btn"
            onClick={onClose}
          >
            ✕
          </button>

        </div>

        <div className="report-form">

          <label>
            Item Name
          </label>

          <input
            type="text"
            value={itemName}
            onChange={(e) =>
              setItemName(e.target.value)
            }
            placeholder="Enter item name"
          />

          <label>
            Category
          </label>

          <select
            value={category}
            onChange={(e) =>
              setCategory(e.target.value)
            }
          >
            <option value="">
              Select Category
            </option>

            <option value="Electronics">
              Electronics
            </option>

            <option value="School Supplies">
              School Supplies
            </option>

            <option value="ID / Documents">
              ID / Documents
            </option>

            <option value="Wallet">
              Wallet
            </option>

            <option value="Bag">
              Bag
            </option>

            <option value="Clothing">
              Clothing
            </option>

            <option value="Accessories">
              Accessories
            </option>

            <option value="Keys">
              Keys
            </option>

            <option value="Others">
              Others
            </option>

          </select>

          <label>
            Location
          </label>

          <input
            type="text"
            value={location}
            onChange={(e) =>
              setLocation(e.target.value)
            }
            placeholder="Where was the item lost or found?"
          />

          <label>
            Description
          </label>

          <textarea
            rows={5}
            value={description}
            onChange={(e) =>
              setDescription(e.target.value)
            }
            placeholder="Describe the item..."
          />

          {errorMessage && (

            <p
              style={{
                color: "#c62828",
                fontSize: "14px",
                marginTop: "10px",
              }}
            >
              {errorMessage}
            </p>

          )}

                    <div className="report-actions">

            <button
              type="button"
              className="cancel-btn"
              onClick={onClose}
              disabled={isSaving}
            >
              Cancel
            </button>

            <button
              type="button"
              className="submit-btn"
              onClick={handleUpdate}
              disabled={isSaving}
            >
              {isSaving
                ? "Updating..."
                : "Update Report"}
            </button>
          </div>
        </div>
      </div>
    </div>

  );

}

export default EditReportedItemModal;