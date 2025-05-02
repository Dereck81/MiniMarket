import React from 'react';

interface SidebarLinkProps {
  onClick: () => void;
  icon: React.ReactNode;
  text: string;
  className?: string;
  active?: boolean;
}

const SidebarLink: React.FC<SidebarLinkProps> = ({ onClick, icon, text, active = false }) => {
  return (
    <button
      onClick={onClick}
      className={`btn w-100 text-start mb-2 d-flex align-items-center gap-2 px-3 py-2 rounded transition ${
        active ? 'bg-white text-success fw-bold shadow-sm' : 'text-white'
      } hover-effect`}
      style={{ backgroundColor: active ? '' : 'transparent', border: 'none' }}
    >
      <div style={{ width: 20 }}>{icon}</div>
      <span>{text}</span>
    </button>
  );
};


export default SidebarLink;
