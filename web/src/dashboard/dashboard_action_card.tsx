import type { ReactNode } from "react";

type Props = {
  title: string;
  icon: ReactNode;
};

function DashboardActionCard({ title, icon }: Props) {
  return (
    <button className="lf-action-card card-report">
      <span>{title}</span>
      <div>{icon}</div>
    </button>
  );
}

export default DashboardActionCard;